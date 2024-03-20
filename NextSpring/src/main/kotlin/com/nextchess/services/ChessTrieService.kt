package com.nextchess.services

import com.nextchess.data.EvaluationResponse
import com.nextchess.data.ScoreMoveResponse
import com.nextchess.datastructures.ChessTrie
import com.nextchess.entities.MasterGame
import com.nextchess.enums.MoveClassification
import com.nextchess.repositories.MasterGameRepository
import com.nextchess.wrappers.Evaluation
import com.nextchess.wrappers.StockfishWrapper
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.LinkedHashMap

@Service
class ChessTrieService(private val masterGameRepo: MasterGameRepository) {
    private val trie: ChessTrie by lazy { initializeTrie() }
    private val stockfish = StockfishWrapper()
    private val cacheSizeLimit: Int = 250

    private fun initializeTrie(): ChessTrie {
        val trie = ChessTrie()
        val games: List<MasterGame> = masterGameRepo.findAll()
        games.forEach { game ->
            trie.insert(game.moves.take(40))
        }
        return trie
    }

    private val evaluationsCache: MutableMap<String, EvaluationResponse> = Collections.synchronizedMap(
        object : LinkedHashMap<String, EvaluationResponse>(16, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, EvaluationResponse>): Boolean {
                return size > cacheSizeLimit
            }
        }
    )

    companion object {
        const val COMMON_MOVE_THRESHOLD = 5000  // If a move has been played more than this, it's common
        const val PLAYABLE_MOVE_THRESHOLD = 1000  // If a move has been played more than this, it's playable
    }

    fun scoreMove(move: String, previousMoves: List<String>, fen: String): ScoreMoveResponse {
        val movesFromTrie = trie.findNextMoves(previousMoves)
//        val stockfish = stockfishPool.take()
        val evaluation = stockfish.evaluate(fen, move)
        return ScoreMoveResponse(classifyMove(evaluation.second, -1), "")
    }


    private fun evaluatePosition(fen: String, move: String): EvaluationResponse {
//        val stockfish = stockfishPool.take()
        try {
            val evaluation = stockfish.evaluate(fen, move)
            return EvaluationResponse(evaluation.second.centipawns, evaluation.second.principalVariation)
        } finally {
//            stockfishPool.offer(stockfish)
        }
    }

    fun evaluatePositionWithCache(fen: String, move: String): EvaluationResponse {
        val cacheKey = "$fen:$move"

        // check if it's in cache
        evaluationsCache[cacheKey]?.let { return it }

        // if it's not in cache, evaluate position
        val evaluation = evaluatePosition(fen, move)

        // store new evaluation in cache
        evaluationsCache[cacheKey] = evaluation
        print("CACHE: $evaluationsCache")

        return evaluation
    }

    fun classifyMove(evaluation: Evaluation, moveFrequency: Int): MoveClassification {
        val pv = evaluation.principalVariation
        val cp = evaluation.centipawns

        if (moveFrequency > COMMON_MOVE_THRESHOLD) {
            return MoveClassification.BOOK_MOVE
        }

        if (cp != null) {
            return when {
//                cp <= BEST_MOVE_THRESHOLD -> MoveClassification.BEST_MOVE
//                cp <= GOOD_MOVE_THRESHOLD -> MoveClassification.VERY_GOOD_MOVE
//                cp <= INACCURACY_THRESHOLD -> MoveClassification.GOOD_MOVE
//                cp > BLUNDER_THRESHOLD -> MoveClassification.BLUNDER
                else -> MoveClassification.INACCURACY
            }
        }

        return MoveClassification.ERROR
    }

    fun nextMovesForSequence(sequence: List<String>, fen: String): List<Map<String, Int>> {
        val nextMoves = trie.findNextMoves(sequence)
        return if (nextMoves.isEmpty()) {
//            val stockfish = stockfishPool.take()
            val (nextMove, eval) = stockfish.evaluate(fen, sequence.last())

            listOf(mapOf<String, Int>(nextMove to -1))
        } else {
            listOf(nextMoves)
        }
    }

//    @PostConstruct
//    fun initialize() {
//        val games: MutableList<MasterGame> = masterGameRepo.findAll()
//        games.forEach { game ->
//            trie.insert(game.moves.take(40))
//        }
//    }
}