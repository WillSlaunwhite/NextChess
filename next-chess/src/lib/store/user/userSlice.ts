import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { RootState } from "../store";

interface UserState {
    theme: string;
}

const initialState: UserState = {
    theme: 'light',
};

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setTheme: (state, action: PayloadAction<string>) => {
            state.theme = action.payload;
        }
    },
})

export const { setTheme } = userSlice.actions;

// selector
export const selectTheme = (state: RootState) => state.user.theme

export default userSlice.reducer;