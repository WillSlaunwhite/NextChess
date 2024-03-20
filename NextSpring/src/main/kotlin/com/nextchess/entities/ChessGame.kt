package com.nextchess.entities

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


data class ChessGame(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val event: String?,
    val site: String?,
    val date: String?,
    val round: String?,
    val white: String,
    val black: String,
    val result: String,
    val whiteElo: String,
    val blackElo: String,
    val eco: String,
    val moves: List<String>,
) {}