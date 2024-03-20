'use client';

import { ThemeProvider, Button } from "@material-tailwind/react";
import { PropsWithChildren } from "react";

interface ButtonProps {
    content: string
}

export const TestButton: React.FC<PropsWithChildren<ButtonProps>> = ({ content }) => {
    return (
        <Button>{content}</Button>
    )
}

export { ThemeProvider, Button };