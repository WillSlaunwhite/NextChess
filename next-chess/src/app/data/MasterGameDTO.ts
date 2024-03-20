interface MasterGameDTO {
    id: string | number;
    event: string;
    white: string;
    black: string;
    result: string;
    eco: string;
}

interface DetailedMasterGameDTO extends MasterGameDTO {
    moves: string[];
    opening: OpeningDTO;
}