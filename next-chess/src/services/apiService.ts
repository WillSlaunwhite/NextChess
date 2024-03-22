import { LineState, GlobalState } from "@/lib/store/chess/chessSlice";
import { convertOpeningVariationsBaseSequenceToFullSequence, convertToFullMoves, getFensFromMoveSequences } from "@/utils/chessUtils";
import { unstable_noStore as noStore } from 'next/cache';

// * EVALUATION

interface EvaluationResponse { evaluation: number, principalVariation: string }

const baseUrl = process.env.NEXT_PUBLIC_API_URL;

export async function fetchEvaluation(fen: string, move: string): Promise<{ bestMove: string, centipawns: number, principalVariation: string }> {
    move = move.split(" ").length > 1 ? move.split(" ")[1] : move;

    try {
        return fetch(`${baseUrl}/api/chess/evaluate`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ fen, move })
        })
            .then(res => res.json())
            .then((data: EvaluationResponse) => {
                console.log(data);
                const bestMove = data.principalVariation.substring(0, 4);
                return { bestMove: bestMove, centipawns: data.evaluation, principalVariation: data.principalVariation };
            });
    } catch (error) {
        console.warn('Failed to fetch evaluation using Stockfish');
        console.error(error);
        return { bestMove: "", centipawns: 0, principalVariation: "" };
    }
}


// * NEXT MOVES

export async function fetchNextMoveForSequence(sequence: string[], fen: string): Promise<string> {
    console.log(sequence + "\n" + fen);
    try {
        const response = await fetch(`${baseUrl}/api/chess/next-moves`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ sequence, fen })
        });

        if (!response.ok) {
            throw new Error(`Server responded with status ${response.status}`);
        }

        const data = await response.json();

        console.log(data);

        const probableMoves = Object.entries(data[0]);
        probableMoves.sort(((a: any, b: any) => b[1] - a[1]));

        const move = probableMoves[0][0];
        if (probableMoves[0][1] === -1) {
            const regex = /^[a-h][1-8][a-h][1-8]$/;
            if (regex.test(move)) {
                return move.substring(0, 2) + ' ' + move.substring(2);
            }

            return move;
        }

        return move.split(' ')[1];
    } catch (error) {
        console.error('Error in fetchNextMoveForSequence:', error);
        throw error; // Rethrow if you want to handle it in the outer catch block
    }
}


// * OPENINGS

export async function fetchAllOpenings(): Promise<OpeningDTO[]> {
    noStore();
    const res = await fetch(`${baseUrl}/api/openings`);
    if (!res.ok) {
        throw new Error(`Error: ${res.status}`);
    }
    return res.json();
}

export async function fetchOpening(openingName: string): Promise<OpeningDTO> {
    const response = await fetch(`http://localhost:8085/api/openings/${openingName}/start`);
    return response.json();
}

export async function processOpeningData(opening: OpeningDTO, lines: LineState[]): Promise<{ global: GlobalState, lines: LineState[] }> {
    const fullMoveSequences = convertOpeningVariationsBaseSequenceToFullSequence(opening).map(sequence => convertToFullMoves(sequence));
    const fens = getFensFromMoveSequences(fullMoveSequences);
    const newLines: LineState[] = [];
    let firstMoves: string[] = [];

    try {
        const results = await Promise.allSettled(
            fullMoveSequences.map(async (sequence, i) => fetchNextMoveForSequence(sequence, fens[i]))
        );

        firstMoves = results.map(result => result.status === "fulfilled" ? result.value : "defaultMove");

        for (let i = 0; i < fullMoveSequences.length; i++) {
            const lineState: LineState = {
                ...lines[i],
                fen: fens[i],
                moveHistory: fullMoveSequences[i],
                nextMove: firstMoves[i]
            }
            newLines.push(lineState);
        }

    } catch (error) {
        console.error("Error processing moves:", error);
        // Handle the error appropriately
    }
    return {
        global: {
            currentLineIndex: 0,
            initialMoves: firstMoves,
            highlightedSquares: [],
            selectedSquare: "",
            variations: opening.variations,
            timerReset: false,
            timerStart: false,
            evaluationsCache: {}
        },
        lines: newLines
    }
}
