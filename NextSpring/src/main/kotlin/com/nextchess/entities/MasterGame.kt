package com.nextchess.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.nextchess.converters.DateConverter
import com.nextchess.converters.ResultConverter
import com.nextchess.converters.RoundConverter
import com.nextchess.converters.StringListConverter
import com.nextchess.enums.Result
import jakarta.persistence.*


@Entity
@Table(name = "master_games")
data class MasterGame(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val event: String? = null,
    val site: String? = null,
    @Convert(converter = DateConverter::class)
    val date: String? = null,
    @Convert(converter = RoundConverter::class)
    val round: String? = null,
    val white: String,
    val black: String,
    @Column(name = "white_elo")
    val whiteElo: Int? = null,
    @Column(name = "black_elo")
    val blackElo: Int? = null,
    @Convert(converter = ResultConverter::class)
    val result: Result,
    val eco: String,
    @Convert(converter = StringListConverter::class)
    val moves: List<String> = listOf(),
    @ManyToOne @JoinColumn(name = "opening_id")
    @JsonBackReference
    val opening: Opening? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MasterGame

        if (id != other.id) return false
        if (event != other.event) return false
        if (site != other.site) return false
        if (date != other.date) return false
        if (white != other.white) return false
        if (black != other.black) return false
        if (whiteElo != other.whiteElo) return false
        if (blackElo != other.blackElo) return false
        if (result != other.result) return false
        if (eco != other.eco) return false
        if (moves != other.moves) return false
        return opening == other.opening
    }

    override fun hashCode(): Int {
        var result1 = id.hashCode()
        result1 = 31 * result1 + (event?.hashCode() ?: 0)
        result1 = 31 * result1 + site.hashCode()
        result1 = 31 * result1 + date.hashCode()
        result1 = 31 * result1 + white.hashCode()
        result1 = 31 * result1 + black.hashCode()
        result1 = 31 * result1 + (whiteElo ?: 0)
        result1 = 31 * result1 + (blackElo ?: 0)
        result1 = 31 * result1 + result.hashCode()
        result1 = 31 * result1 + eco.hashCode()
        result1 = 31 * result1 + moves.hashCode()
        result1 = 31 * result1 + opening.hashCode()
        return result1
    }

    override fun toString(): String {
        return "MasterGame(id=$id, event=$event, site='$site', date=$date, round=$round, white='$white', black='$black', whiteElo=$whiteElo, blackElo=$blackElo, result=$result, eco='$eco', moves=$moves)"
    }
}
