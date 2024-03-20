import { TestButton } from '@/app/ui/button';

interface HomeProps {
  items: string
}

export const HomeComponent: React.FC<HomeProps> = ({ items }) => {
  return (
    <div className='min-h-screen'>
      <TestButton content={'hello'} />
      <h1>{items}</h1>
    </div>
  );
}
