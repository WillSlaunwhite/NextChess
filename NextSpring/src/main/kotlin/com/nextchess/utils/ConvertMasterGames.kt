package com.nextchess.utils

import com.nextchess.data.DetailedMasterGameDTO
import com.nextchess.data.MasterGameDTO
import com.nextchess.entities.MasterGame


fun convertToMasterGameDTO(masterGame: MasterGame): MasterGameDTO {
    return MasterGameDTO(
        id = masterGame.id,
        event = masterGame.event,
        white = masterGame.white,
        black = masterGame.black,
        result = masterGame.result,
        eco = masterGame.eco
    )
}

fun convertToDetailedMasterGameDTO(masterGame: MasterGame): DetailedMasterGameDTO {
    return DetailedMasterGameDTO(
        id = masterGame.id,
        event = masterGame.event,
        white = masterGame.white,
        black = masterGame.black,
        result = masterGame.result,
        eco = masterGame.eco,
        moves = masterGame.moves
    )
}
