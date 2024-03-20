package com.nextchess.data

data class ScoreMoveRequest(val move: String, val previousMoves: List<String>, val fen: String)