package com.nextchess.data

import com.nextchess.entities.Variation


class DetailedOpeningDTO(id: Long, name: String, description: String?, baseMovesSequence: List<String>, difficulty: String?, val masterGames: List<MasterGameDTO>, variations: List<Variation>) : OpeningDTO(id, name, description, baseMovesSequence, variations, difficulty)