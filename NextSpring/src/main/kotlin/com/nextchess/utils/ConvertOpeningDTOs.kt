package com.nextchess.utils

import com.nextchess.data.DetailedOpeningDTO
import com.nextchess.data.OpeningDTO
import com.nextchess.entities.Opening

fun convertToOpeningDTO(opening: Opening): OpeningDTO {
    return OpeningDTO(
        id = opening.id,
        name = opening.name,
        description = opening.description,
        baseMovesSequence = opening.baseMoveSequence,
        difficulty = opening.difficulty,
        variations = opening.variations,
    )
}

fun convertToDetailedOpeningDTO(opening: Opening): DetailedOpeningDTO {
    return DetailedOpeningDTO(
        id = opening.id,
        name = opening.name,
        description = opening.description,
        baseMovesSequence = opening.baseMoveSequence,
        masterGames = opening.masterGames.map { convertToMasterGameDTO(it) },
        difficulty = opening.difficulty,
        variations = opening.variations,
    )
}
