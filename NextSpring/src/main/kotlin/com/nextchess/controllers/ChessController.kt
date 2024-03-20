package com.nextchess.controllers

import com.nextchess.data.*
import com.nextchess.services.ChessTrieService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/api/chess")
class ChessController(private val chessTrieService: ChessTrieService) {

    @RequestMapping("", "/", "/home")
    fun index(): String? {
        return "forward:/index.html"
    }

    @PostMapping("/evaluate")
    fun validateMove(@RequestBody move: Move): ResponseEntity<EvaluationResponse> {
        val evaluation = chessTrieService.evaluatePositionWithCache(move.fen, move.move)
        return ResponseEntity.ok(evaluation)
    }

    @PostMapping("/next-moves")
    fun getNextMoves(@RequestBody request: NextMoveRequest): ResponseEntity<List<Map<String, Int>>> {
        return ResponseEntity.ok(chessTrieService.nextMovesForSequence(request.sequence, request.fen))
    }

    @PostMapping("/score-move")
    fun scoreMove(@RequestBody request: ScoreMoveRequest): ResponseEntity<ScoreMoveResponse> {
        return ResponseEntity.ok(chessTrieService.scoreMove(request.move, request.previousMoves, request.fen))
    }
}