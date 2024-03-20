'use server';

import { fetchAllOpenings } from "@/app/services/apiService";
import OpeningsMenu from "@/app/ui/openings";

export default async function Page() {
    // const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/openings`);
    // const data = res.json().then((openings: OpeningDTO[]) => openings);
    // const data = await res.json();
    const data = await fetchAllOpenings();
    console.log("IN OVERVIEW");
    
    // let data;
    // try {
    //     // const res = await fetch(`${process.env.API_URL}/api/openings`);
    //     const res = await fetch("http://localhost:8085/api/openings");
    //     if (!res.ok) {
    //         throw new Error(`Failed to fetch, status: ${res.status}`);
    //     }
    //     console.log("in overview page \n" + res.json())
    //     data = await res.json();
    // } catch (error) {
    //     console.error("Fetching error:", error);
    //     // Handle error or set fallback data
    // }

    return (
        <main className="w-full flex flex-col items-center justify-center gap-1 mt-1">
            <OpeningsMenu openings={data} />
        </main>
    )
}