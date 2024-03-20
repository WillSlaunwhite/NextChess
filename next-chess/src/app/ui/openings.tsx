'use client';

import React from 'react';
import { Card, List, ListItem, Typography } from '../../../providers';
import { Lusitana } from 'next/font/google';

interface OpeningsMenuProps {
    openings: OpeningDTO[];
}

const lusi = Lusitana({ subsets: ["latin"], weight: "400" });

const OpeningsMenu: React.FC<OpeningsMenuProps> = ({ openings }) => {
    
    return (
        <div className={`${lusi.className} min-h-screen`}>
            <Typography variant="h4" className={`${lusi.className}`}>Select an Opening to Practice</Typography>
            <Card className="w-5/6 opacity-80 mt-3">
                <List className="w-full p-0">
                    {openings.map((opening, i) => (
                        <ListItem key={i} className="ripple-bg-blue-700 ripple text-center p-4"><Typography variant="h4" className="w-full">{opening.name}</Typography></ListItem>
                    ))}
                </List>
            </Card>
        </div>
    )
}

export default OpeningsMenu