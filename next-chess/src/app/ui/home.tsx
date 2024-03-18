interface HomeProps {
  items: string
}

export const HomeComponent: React.FC<HomeProps> = ({ items }) => {
  return (
    <div>
      <h1>{items}</h1>
    </div>
  );
}
