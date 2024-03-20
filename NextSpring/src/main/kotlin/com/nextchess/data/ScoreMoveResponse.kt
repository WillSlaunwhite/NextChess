package com.nextchess.data

import com.nextchess.enums.MoveClassification

data class ScoreMoveResponse(val classification: MoveClassification, val feedback: String)
