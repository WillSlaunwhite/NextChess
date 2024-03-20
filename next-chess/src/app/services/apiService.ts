export async function fetchAllOpenings(): Promise<OpeningDTO[]> {
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/openings`);
    if (!res.ok) {
        throw new Error(`Error: ${res.status}`);
    }
    const data = res.json();
    console.log("in api service \n" + data);
    return data;
}