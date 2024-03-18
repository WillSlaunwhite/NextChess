import { Button, TestButton } from '@/app/ui/button';

interface HomeProps {
  items: string
}

interface ButtonProps {
  children: React.ReactNode;
}

export const HomeComponent: React.FC<HomeProps> = ({ items }) => {
  return (
    <div>
      <TestButton content={'hello'} />
      <h1>{items}</h1>
    </div>
  );
}
