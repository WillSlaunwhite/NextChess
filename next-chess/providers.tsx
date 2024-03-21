'use client';

export { ThemeProvider, Typography, Button, List, Card, ListItem } from "@material-tailwind/react";
import { store } from "@/lib/store/store";
import { Provider } from 'react-redux';

export default function ReduxProvider({ children }: { children: React.ReactNode; }) { return <Provider store={store}>{children}</Provider> }