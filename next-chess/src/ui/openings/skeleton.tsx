import { shimmer } from "../skeleton";

// OpeningCardSkeleton.tsx
import React from 'react';

const OpeningCardSkeleton: React.FC<{ count: number }> = ({ count }) => {
    return (
        <>
            {[0, 1, 2].map((i) => (
                <div key={i} className={`${shimmer} relative overflow-hidden rounded-md bg-gray-300 p-2 shadow-sm  w-5/6 h-16 flex justify-center`} >
                    <div className="flex items-center justify-center truncate rounded-lg bg-gray-200 p-4 w-full">
                        <div className="h-7 w-5/6 rounded-md bg-gray-300" />
                    </div>
                </div>)
            )}
        </>
    );
};

export default OpeningCardSkeleton;