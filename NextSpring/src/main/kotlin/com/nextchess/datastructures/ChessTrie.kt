package com.nextchess.datastructures


class ChessTrie {
    private val root = ChessTrieNode()

    fun insert(moveSequence: List<String>) {
        var current = root
        for (move in moveSequence) {
            current = current.children.getOrPut(move) { ChessTrieNode() }
            current.frequency++
        }
    }

    fun findNextMoves(moves: List<String>): Map<String, Int> {
        var current = root

        // Check if the last move is a half-move
        val isLastMoveHalf = """^[a-h][1-8]$|^[NBRQKP][a-h][1-8]$""".toRegex().matches(moves.last())

        // If the last move is a half-move, remove it from the sequence for now
        val processedMoves = if (isLastMoveHalf) moves.dropLast(1) else moves

        for (move in processedMoves) {
            if (!current.children.containsKey(move)) {
                return mapOf()
            }
            if (current.children[move] != null) {
                current = current.children[move]!!
            } else {
                return mapOf();
            }
        }

        if (isLastMoveHalf) {
            val lastMove = moves.last()

            return current.children
                .filterKeys { it.startsWith(lastMove) }
                .mapValues { it.value.frequency }
        }

        return current.children.mapValues { it.value.frequency }
    }

    class ChessTrieNode {
        var frequency: Int = 0
        var children: MutableMap<String, ChessTrieNode> = mutableMapOf()
    }

    fun printTrie(node: ChessTrieNode, prefix: String = "") {
        println("$prefix${if (node.frequency > 0) "($node.frequency)" else ""}")
        for ((move, child) in node.children) {
            printTrie(child, prefix + move + " -> ")
        }
    }
}