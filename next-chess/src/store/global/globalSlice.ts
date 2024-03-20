import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { initialGlobalState } from "../chess/chessSlice";

export const globalSlice = createSlice({
    name: 'global',
    initialState: initialGlobalState,
    reducers: {
        addEvaluationToCache: (state, action: PayloadAction<{ key: string, evaluation: number }>) => {
            // * KEY IS FEN + MOVE
            const { key, evaluation } = action.payload;

            state.evaluationsCache = {
                ...state.evaluationsCache,
                [key]: evaluation
            }
        },
        clearSelectedSquares: (state, action: PayloadAction<void>) => {
            state.selectedSquare = "";
        },
        setCurrentLineIndex: (state, action: PayloadAction<number>) => {
            state.currentLineIndex = action.payload;
        },
    }
});

export const { addEvaluationToCache, clearSelectedSquares, setCurrentLineIndex } = globalSlice.actions;

export default globalSlice.reducer;