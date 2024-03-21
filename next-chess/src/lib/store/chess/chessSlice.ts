import { PayloadAction, createSlice } from "@reduxjs/toolkit";

const startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

export interface GlobalState {
    currentLineIndex: number,
    initialMoves: string[],
    highlightedSquares: string[],
    selectedSquare: string,
    variations: VariationDTO[],
    timerStart: boolean,
    timerReset: boolean,
    evaluationsCache: Record<string, number>
}

export interface LineState {
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

export interface GameState {
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
    initialState: initialState,
    reducers: {
        deactivateLine: (state, action: PayloadAction<number>) => {
            state.lines[action.payload].isActive = false;
        },
        initGame: (state, action: PayloadAction<{global: GlobalState; lines: LineState[]}>) => {
            state.global = action.payload.global;
            state.lines = action.payload.lines;
        },
        updateLineState: (state, action: PayloadAction<{ index: number, lineState: LineState }>) => {
            const { index, lineState } = action.payload;
            state.lines[index] = lineState;
        },
    }
})

export const { deactivateLine, initGame, updateLineState } = chessSlice.actions;

export default chessSlice.reducer;