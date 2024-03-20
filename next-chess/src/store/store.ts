import { configureStore } from '@reduxjs/toolkit';
import userReducer from '@/store/user/userSlice'
import chessReducer from '@/store/chess/chessSlice';
import globalReducer from '@/store/global/globalSlice';

export const store = configureStore({
    reducer: {
        user: userReducer,
        global: globalReducer,
    }
})

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;