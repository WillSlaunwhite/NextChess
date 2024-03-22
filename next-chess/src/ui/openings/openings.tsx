'use client';

import { initGame } from '@/lib/store/chess/chessSlice';
import { RootState, useAppDispatch, useAppSelector } from '@/lib/store/store';
import { fetchOpening, processOpeningData } from '@/services/apiService';
import { Lusitana } from 'next/font/google';
import { useRouter } from 'next/navigation';
import { Suspense } from 'react';
import { Typography } from '../../../providers';
import OpeningsList from './openings-list';
import OpeningCardSkeleton from './skeleton';


const lusi = Lusitana({ subsets: ["latin"], weight: "400" });

const OpeningsMenu = () => {
    // * REDUX
    const dispatch = useAppDispatch();
    const lines = useAppSelector((state: RootState) => state.chess.lines);

    // * NEXT/REACT
    const router = useRouter();


    const handleOpeningSelect = async (openingName: string) => {
        try {
            const opening: OpeningDTO = await fetchOpening(openingName);
            const gameData = await processOpeningData(opening, lines);
            dispatch(initGame(gameData));
            router.push('/game');
        } catch (error) {
            console.error('Failed to fetch variations: ', error);
        }
    }

    return (
        <main className='h-3/5 w-1/2 bg-gray-500 gap-8 flex flex-col mt-4 mx-auto items-center rounded-md'>
            <Typography variant="h3" className={`${lusi.className} py-2 text-center w-2/3`}>Select an Opening to Practice</Typography>
            <Suspense fallback={<OpeningCardSkeleton count={1}/>}>
                <OpeningsList onOpeningSelect={handleOpeningSelect}/>
            </Suspense>
        </main>
    )
}

export default OpeningsMenu