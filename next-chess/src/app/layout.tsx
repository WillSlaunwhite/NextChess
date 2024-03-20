import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import { Provider, ThemeProvider } from '../../providers';
import { store } from "@/store/store";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "NextChess",
  description: "Practice Chess openings.",
};

export default function RootLayout({ children, }: Readonly<{ children: React.ReactNode; }>) {
  return (
    <html lang="en">
      {/* <Provider store={store}> */}
        <ThemeProvider>
          <body className={`${inter.className} antialiased`}>{children}</body>
        </ThemeProvider>
      {/* </Provider> */}
    </html>
  );
}
