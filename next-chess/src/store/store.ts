import globalReducer from '@/store/global/globalSlice';
import userReducer from '@/store/user/userSlice';
import chessReducer from '@/store/chess/chessSlice';
import { configureStore } from '@reduxjs/toolkit';

export const store = configureStore({
    reducer: {
        user: userReducer,
        global: globalReducer,
        chess: chessReducer,
    }
})

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;