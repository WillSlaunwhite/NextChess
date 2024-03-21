import OpeningsMenu from '../openings/openings';

export const HomeComponent: React.FC = async () => {
  return (
    <main className="h-screen w-full bg-gray-300">
      <OpeningsMenu />
    </main>
  );
}
