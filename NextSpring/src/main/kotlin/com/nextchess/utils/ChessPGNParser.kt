package com.nextchess.utils

import com.nextchess.entities.MasterGame
import com.nextchess.entities.Opening
import com.nextchess.enums.Result
import java.io.File
import java.util.*

//fun main() {
//    println("Parsed ${games.size} games")
//    games.forEach { game ->
//        println("Game between ${game.white} and ${game.black} ended with result ${game.result}. Moves: ${game.moves}")
//    }
//}

fun isGameResult(line: String): Boolean {
    return line.contains(Regex("[0-1]-[0-1]|1/2-1/2"))
}

fun parsePGN(pgnContent: List<String>): List<MasterGame> {
    val games = mutableListOf<MasterGame>()
    val currentGameMetadata = mutableMapOf<String, String>()
    val currentMoves = mutableListOf<String>()

    for (line in pgnContent) {
        when {
            line.startsWith("[") -> {
                val key = line.substringAfter("[").substringBefore(" ").trim()
                val value = line.substringAfter("\"").substringBeforeLast("\"").trim()
                currentGameMetadata[key] = value
            }

            isGameResult(line) -> {
                val movesWithResult = line.trim().split(" ")
                val result = movesWithResult.last() // The last element is the game result
                val movesWithoutResult = movesWithResult.dropLast(1).joinToString(" ")

                val moveMatcher =
                    Regex("(\\d+\\.)\\s*([\\w\\+\\#\\-\\=]+)\\s*([\\w\\+\\#\\-\\=]*)").findAll(movesWithoutResult)

                for (match in moveMatcher) {
                    currentMoves.add(match.groupValues[2])
                    if (match.groupValues[3].isNotEmpty()) {
                        currentMoves.add(match.groupValues[3])
                    }
                }

                if (result != " ") {
                    val parsedDate = currentGameMetadata["Date"] ?: ""
                    val dateToUse = if (isValidDate(parsedDate)) parsedDate else null

                    val res =
                        if (result == "1-0") Result.WHITE else if (result == "0-1") Result.BLACK else Result.DRAW
                    val game = MasterGame(
                        event = currentGameMetadata["Event"],
                        site = currentGameMetadata["Site"],
                        date = dateToUse,
                        round = currentGameMetadata["Round"] ?: "",
                        white = currentGameMetadata["White"] ?: "",
                        black = currentGameMetadata["Black"] ?: "",
                        result = res,
                        whiteElo = currentGameMetadata["WhiteElo"]?.toIntOrNull(),
                        blackElo = currentGameMetadata["BlackElo"]?.toIntOrNull(),
                        eco = currentGameMetadata["ECO"] ?: "",
//                        moves = if (currentMoves.toList().size < 20) currentMoves.toList() else currentMoves.toList()
//                            .subList(0, 20),
                        moves = currentMoves.toList(),
                        opening = Opening(
                            2,
                            "Two Knights Defense",
                            "The Two Knights Defense is a dynamic and aggressive response to the Italian Game. After 1.e4 e5 2.Nf3 Nc6 3.Bc4, Black immediately challenges White's central pawn with 3...Nf6. This opening often leads to sharp play with both sides having chances for a kingside attack. It's named for the two knights that are developed by move 3, and it has been a favorite of many top players throughout history.",
                            listOf("1.e4 e5", "2.Nf3 Nc6", "3.Bc4 Nf6"),
                            mutableListOf(),
                            listOf()
                        ),
                    )
                    games.add(game)
                    currentGameMetadata.clear()
                    currentMoves.clear()
                }

            }

            else -> {
                val moveMatcher = Regex("(\\d+\\.)\\s*([\\w\\+\\#\\-\\=]+)\\s*([\\w\\+\\#\\-\\=]*)").findAll(line)

                for (match in moveMatcher) {
                    currentMoves.add(match.groupValues[2])
                    if (match.groupValues[3].isNotEmpty()) {
                        currentMoves.add(match.groupValues[3])
                    }
                }


            }

        }
    }
    return games
}

fun readAndParsePGN(filePath: String): List<MasterGame> {
    val lines = File(filePath).readLines()
    return parsePGN(lines)
}

fun isValidDate(date: String): Boolean {
    val parts = date.split(".")
    if (parts.size != 3) return false

    val year = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val day = parts[2].toIntOrNull() ?: return false

    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day)

    return year == calendar.get(Calendar.YEAR) &&
            month == calendar.get(Calendar.MONTH) + 1 &&
            day == calendar.get(Calendar.DAY_OF_MONTH)
}
