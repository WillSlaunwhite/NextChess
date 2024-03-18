import { HomeComponent } from "@/app/ui/home";

export default async function Page() {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/test`);
    const data = await res.text();

    return (
        <HomeComponent items={data} />
    )
}