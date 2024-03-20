import { Chess, Move, Piece, Square } from "chess.js";
import { LineState } from "@/store/chess/chessSlice";


export function appendToMoveHistory(history: string[], san: string): string[] {
    const halfMoves = convertToHalfMoves(history);

    if (halfMoves.length % 2 === 0) {
        const newHistory = [...history];
        newHistory.push(san);
        return newHistory;
    } else {
        const lastMove = history[history.length - 1];
        const newHistory = history.slice(0, -1);
        newHistory.push(`${lastMove} ${san}`);
        return newHistory;
    }
}

// export function convertSanToFullMove(san: string): string[] {

// }

export function convertToHalfMoves(history: string[]): string[] {
    return history.flatMap(sequence => sequence.split(" "));
}

export function convertToFullMoves(history: string[]): string[] {
    const fullMoves = [];
    for (let i = 0; i < history.length; i += 2) {
        if (history[i + 1]) {
            fullMoves.push(`${history[i]} ${history[i + 1]}`);
        } else {
            fullMoves.push(history[i]);
        }
    }

    return fullMoves;
};

export function convertOpeningVariationsBaseSequenceToFullSequence(opening: OpeningDTO): string[][] {
    const baseSequence = splitMoveString(opening.baseMovesSequence[0]);
    return opening.variations.map((variation: VariationDTO) => {
        if (variation.movesSequence[0]) {
            return baseSequence.concat(variation.movesSequence);
        } else {
            return baseSequence;
        }
    });
}

export const fenToArray = (fen: string): (string | null)[][] => {
    const rows = fen.split("/");
    return rows.map((row) => {
        let squares: (string | null)[] = [];
        for (const char of row) {
            if (isNaN(parseInt(char))) {
                // If it's a piece
                squares.push(char);
            } else {
                // If it's a number, add that many null values
                squares = squares.concat(Array(parseInt(char)).fill(null));
            }
        }
        return squares;
    });
};

export function getFensFromMoveSequences(moveSequences: string[][]): string[] {
    const tempGame = new Chess();
    const fens: string[] = [];
    moveSequences.forEach(sequence => {
        sequence.forEach(movePair => {
            if (movePair !== "") {
                const moves: string[] = movePair.split(" ");
                moves.forEach(move => {
                    if (move !== "") {
                        tempGame.move(move);
                    }
                })
            }
        });

        if (!fens.includes(tempGame.fen())) {
            fens.push(tempGame.fen());
        }
        tempGame.reset();
    });

    return fens;
}

export function getLastMoveSquares(moveHistory: string[]): { from: string, to: string } {
    const tempGame = new Chess();
    const moves = convertToHalfMoves(moveHistory);
    for (let i = 0; i < moves.length - 1; i++) {
        tempGame.move(moves[i]);
    }

    const move = tempGame.move(moves[moves.length - 1]);
    return { from: move.from, to: move.to };
}

export function getMoveNumber(fen: string): number {
    const tempGame = new Chess(fen);
    return tempGame.moveNumber();
}

export function getPieceAtSquare(fen: string, square: string): Piece {
    const tempGame = new Chess(fen);
    return tempGame.get(square as Square);
}

export function getPossibleMovesForSquare(fen: string, square: string): string[] {
    const tempGame = new Chess(fen);
    return tempGame.moves({ square: square as Square, verbose: true }).map(move => move.to);
}

export function getProbableMove(moveData: Record<string, number>): string {
    let mostProbableMove = '';
    let highestOccurrence = 0;

    for (const move in moveData) {
        if (moveData[move] > highestOccurrence) {
            highestOccurrence = moveData[move];
            mostProbableMove = move;
        }
    }

    return mostProbableMove;
}

export function isCheckmate(fen: string): boolean {
    const tempGame = new Chess(fen);
    return tempGame.isCheckmate();
}

export function isDraw(fen: string): boolean {
    const tempGame = new Chess(fen);
    return tempGame.isDraw();
}

export function isComputersTurn(moveSequence: string[], computerColor: string): boolean {
    const splitMoveSequence = convertToHalfMoves(moveSequence);
    const isWhite = computerColor === 'white' || computerColor === 'w';
    return (isWhite && splitMoveSequence.length % 2 === 0) || (!isWhite && splitMoveSequence.length % 2 === 1);
}

export function isPromotion(move: Move): boolean {
    return move.flags.includes('p');
}

export const isValidMove = (fen: string, source: string, destination: string): boolean => {
    const tempGame = new Chess(fen);
    const validMoves = tempGame.moves({ square: source as Square, verbose: true });
    return validMoves.some(move => move.to === destination);
};

export function splitMoveString(moves: string): string[] {
    return moves
        .split(/\d+\./) // split based on move numbers
        .join(' ')
        .split(/\s+/)
        .filter(Boolean); // remove empty strings
}

export function undoMove(moveHistory: string[]): string {
    const tempGame = new Chess();
    moveHistory.forEach((move) => tempGame.move(move));
    const oldFen = tempGame.fen();
    const undoResult = tempGame.undo();
    
    if (undoResult) {
        return undoResult.after;
    } else {
        return oldFen;
    }
    
}

export function updateLineState(lines: LineState[], lineIndex: number, moveDetails: MoveDetails): LineState[] {
    const updatedLines = [...lines];
    const currentLine = updatedLines[lineIndex];
    const { updatedMoveHistory, isComputerTurn } = updateMoveHistoryAndCheckComputerTurn(currentLine, moveDetails.san);

    updatedLines[lineIndex] = {
        ...currentLine,
        fen: moveDetails.fen,
        isPawnPromotion: moveDetails.isPromotion,
        moveHistory: updatedMoveHistory,
        san: moveDetails.san,
        isComputerTurn,
        isComputerReadyToMove: false,
    }

    return updatedLines;
}

export function updateLineStateForComputer(lines: LineState[], lineIndex: number, moveDetails: MoveDetailsComputer): LineState[] {
    const updatedLines = [...lines];
    const currentLine = updatedLines[lineIndex];

    const { updatedMoveHistory, isComputerTurn } = updateMoveHistoryAndCheckComputerTurn(currentLine, moveDetails.san);

    updatedLines[lineIndex] = {
        ...currentLine,
        fen: moveDetails.fen,
        isPawnPromotion: moveDetails.isPromotion,
        moveHistory: updatedMoveHistory,
        san: moveDetails.san,
        isComputerTurn,
        isComputerReadyToMove: false,
        nextMove: moveDetails.nextMove,
    }

    return updatedLines;
}

export function updateMoveHistoryAndCheckComputerTurn(line: LineState, san: string): { updatedMoveHistory: string[], isComputerTurn: boolean } {
    const updatedMoveHistory = appendToMoveHistory(line.moveHistory, san);
    const isComputerTurn = isComputersTurn(updatedMoveHistory, line.computerColor);
    return { updatedMoveHistory, isComputerTurn };
}




interface MoveDetailsComputer {
    fen: string;
    san: string;
    isPromotion: boolean;
    nextMove: string;
}

interface MoveDetails {
    fen: string;
    san: string;
    isPromotion: boolean;
}