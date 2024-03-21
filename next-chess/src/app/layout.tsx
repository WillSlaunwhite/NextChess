import type { Metadata } from "next";
import { Inter } from "next/font/google";
import ReduxProvider, { ThemeProvider } from '../../providers';
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "NextChess",
  description: "Practice Chess openings.",
};

export default function RootLayout({ children, }: Readonly<{ children: React.ReactNode; }>) {
  return (
    <html lang="en">
      <ReduxProvider>
        <ThemeProvider>
          <body className={`${inter.className} antialiased min-h-screen`}>{children}</body>
        </ThemeProvider>
      </ReduxProvider>
    </html>
  );
}
