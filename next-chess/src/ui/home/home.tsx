import { fetchAllOpenings } from '@/services/apiService';
import OpeningsMenu from '../openings/openings';

export const HomeComponent: React.FC = async () => {
  const data = await fetchAllOpenings();
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <OpeningsMenu openings={data} />
    </main>
  );
}
