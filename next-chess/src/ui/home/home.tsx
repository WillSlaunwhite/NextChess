import { Suspense } from 'react';
import OpeningsMenu from '../openings/openings';
import OpeningCardSkeleton from '../openings/skeleton';

export const HomeComponent: React.FC = () => {
  return (
    <main className="h-screen w-full ">
      <OpeningsMenu />
    </main>
  );
}
