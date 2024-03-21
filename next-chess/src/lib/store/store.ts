import chessReducer from '@/lib/store/chess/chessSlice';
import globalReducer from '@/lib/store/global/globalSlice';
import userReducer from '@/lib/store/user/userSlice';
import { configureStore } from '@reduxjs/toolkit';
import { TypedUseSelectorHook, useSelector } from 'react-redux';
import { useDispatch } from 'react-redux';

export const store = configureStore({
    reducer: {
        user: userReducer,
        global: globalReducer,
        chess: chessReducer,
    }
});


export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;