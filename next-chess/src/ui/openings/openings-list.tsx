import { fetchAllOpenings } from '@/services/apiService';
import { useEffect, useState } from 'react';
import { Card, List, ListItem, Typography } from '../../../providers';
import Link from 'next/link';

interface OpeningsListProps {
    onOpeningSelect: (openingName: string) => Promise<void>
}

const OpeningsList: React.FC<OpeningsListProps> = async ({ onOpeningSelect }) => {
    // * REDUX
    const openings = await fetchAllOpenings();

    return (
        <List className="w-full p-0 h-5/6">
            {openings.map((opening, i) => (
                <Card key={i} className="w-5/6 opacity-80 bg-gray-300 rounded-md mx-auto" >
                    <ListItem className="text-center p-4" onClick={() => onOpeningSelect(opening.name)}>
                        <Typography variant="h4" className="w-full">{opening.name}</Typography>
                    </ListItem>
                </Card>
            ))}
        </List>
    );
};

export default OpeningsList;