'use client';

import { initGame } from '@/lib/store/chess/chessSlice';
import { RootState } from '@/lib/store/store';
import { fetchAllOpenings, fetchOpening, processOpeningData } from '@/services/apiService';
import { Lusitana } from 'next/font/google';
import { useRouter } from 'next/navigation';
import { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Card, List, ListItem, Typography } from '../../../providers';
import { useAppDispatch, useAppSelector } from '@/lib/hooks/hooks';


const lusi = Lusitana({ subsets: ["latin"], weight: "400" });

const OpeningsMenu: React.FC = () => {
    const store = useAppSelector((state) => state.chess);
    const initialized = useRef(false);
    
    const lines = useAppSelector((state: RootState) => state.chess.lines);
    const dispatch = useAppDispatch();

    const [openings, setOpenings] = useState<OpeningDTO[]>([]);
    const router = useRouter();

    const openGame = async (openingName: string) => {
        try {
            const opening: OpeningDTO = await fetchOpening(openingName);
            // if (!initialized.current) {
            //     store.dispatch
            // }
            console.log(opening);
            const gameData = await processOpeningData(opening, lines);
            console.log(gameData);

            dispatch(initGame(gameData));

            router.push('/game');
        } catch (error) {
            console.error('Failed to fetch variations: ', error);
        }
    }

    useEffect(() => {
        const data = fetchAllOpenings();
        data
            .then((openings) => setOpenings(openings))
            .catch((error) => console.error("Failed to fetch openings: ", error))
    }, []);

    return (
        <main className='h-3/5 w-1/2 bg-gray-500 gap-8 flex flex-col mt-4 mx-auto items-center rounded-md riple ripple-bg-blue-700'>
            <Typography variant="h3" className={`${lusi.className} py-2 text-center w-2/3`}>Select an Opening to Practice</Typography>
            <Card className="w-5/6 opacity-80 bg-gray-300 ripple-bg-blue-700 ripple">
                <List className="w-full p-0">
                    {openings.map((opening, i) => (
                        <ListItem key={i} className="ripple-bg-blue-700 ripple text-center p-4" onClick={() => { openGame(opening.name) }}><Typography variant="h4" className="w-full">{opening.name}</Typography></ListItem>
                    ))}
                </List>
            </Card>
        </main>
    )
}

export default OpeningsMenu