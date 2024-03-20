import { PayloadAction, createSlice } from "@reduxjs/toolkit";

const startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

interface GlobalState {
    currentLineIndex: number,
    initialMoves: string[],
    highlightedSquares: string[],
    selectedSquare: string,
    variations: VariationDTO[],
    timerStart: boolean,
    timerReset: boolean,
    evaluationsCache: Record<string, number>
}

interface LineState {
    colorOfPiece: string,
    computerColor: "black" | "white",
    evaluation: number,
    fen: string,
    isActive: boolean,
    isComputerTurn: boolean,
    isComputerReadyToMove: boolean,
    isPawnPromotion: boolean,
    lastMoveValid: boolean,
    moveHistory: string[],
    nextMove: string,
    pieceAtSquare: string,
    promotionDestination: string,
    promotionSource: string,
    san: string,
};

interface GameState {
    global: GlobalState;
    lines: LineState[];
}

export const initialGlobalState: GlobalState = {
    currentLineIndex: 0,
    initialMoves: [],
    highlightedSquares: [],
    selectedSquare: "",
    variations: [],
    timerStart: false,
    timerReset: false,
    evaluationsCache: {}
};

// Define the initial state for a single line
const defaultLine: LineState = {
    colorOfPiece: "",
    computerColor: "black",
    evaluation: 0,
    fen: startingFen,
    isActive: true,
    isComputerTurn: false,
    isComputerReadyToMove: false,
    isPawnPromotion: false,
    lastMoveValid: false,
    moveHistory: [],
    nextMove: "",
    pieceAtSquare: "",
    promotionDestination: "",
    promotionSource: "",
    san: "",
};

// The initial state for the Redux store, combining global state and lines
const initialState: GameState = {
    global: initialGlobalState,
    lines: [defaultLine, defaultLine, defaultLine] // Start with three lines as in your context
};

export const chessSlice = createSlice({
    name: 'chess',
    initialState: [defaultLine, defaultLine, defaultLine],
    reducers: {
        updateLineState: (state, action: PayloadAction<{index: number, lineState: LineState}>) => {
            const { index, lineState }= action.payload;
            state[index] = lineState;
        }
    }
})


export default chessSlice.reducer;