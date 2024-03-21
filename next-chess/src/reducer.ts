import chessReducer from '@/lib/store/chess/chessSlice';
import userReducer from '@/lib/store/user/userSlice';
import { Action } from "redux";

export default function rootReducer(state = {}, action: Action) {
    return {
        chess: chessReducer,
        user: userReducer
    }
}