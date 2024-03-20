interface OpeningDTO {
    id: string | number;
    name: string;
    description: string;
    baseMovesSequence: string;
    variations: VariationDTO[];
    difficulty: string | number;
}

interface DetailedOpeningDTO extends OpeningDTO {
    masterGames: MasterGameDTO[];
}
