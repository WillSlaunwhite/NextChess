import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { initialGlobalState } from "../chess/chessSlice";

export const globalSlice = createSlice({
    name: 'global',
    initialState: initialGlobalState,
    reducers: {
        setCurrentLineIndex: (state, action: PayloadAction<number>) => {
            state.currentLineIndex = action.payload;
        },
        clearSelectedSquares: (state, action: PayloadAction<void>) => {
            state.selectedSquare = "";
        }
    }
});

export const { setCurrentLineIndex, clearSelectedSquares } = globalSlice.actions;

export default globalSlice.reducer;