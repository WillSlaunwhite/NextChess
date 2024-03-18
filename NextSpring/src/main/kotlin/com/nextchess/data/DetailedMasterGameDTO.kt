package com.nextchess.data

import com.nextchess.enums.Result

class DetailedMasterGameDTO(id: Long, event: String?, white: String, black: String, result: Result, eco: String, val moves: List<String>) : MasterGameDTO(id, event, white, black, result, eco)
