import { store } from "@/store/store";
import { AppType } from "next/app";
import { Provider } from "react-redux";


const App: AppType = ({ Component, pageProps }) => {
    return (
        <Provider store={store}>
            <Component {...pageProps} />
        </Provider>
    )
}

export default App;