interface VariationDTO {
    id: string | number;
    name: string;
    movesSequence: string[];
    opening: OpeningDTO | undefined;
}