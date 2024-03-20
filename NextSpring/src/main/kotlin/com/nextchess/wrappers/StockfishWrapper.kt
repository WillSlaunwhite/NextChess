package com.nextchess.wrappers

import jakarta.annotation.PreDestroy
import org.springframework.scheduling.annotation.Scheduled
import java.io.*
import java.time.Duration
import java.time.Instant


class StockfishWrapper {
    private val possiblePaths = listOf("/home/linuxbrew/.linuxbrew/bin/stockfish", "/usr/local/bin/stockfish", "stockfish")
    private lateinit var processBuilder: ProcessBuilder
    private lateinit var process: Process
    private lateinit var reader: BufferedReader
    private lateinit var writer: BufferedWriter
    private var isEngineRunning: Boolean = false
    private var lastUsed: Instant = Instant.now()

    init {
        var foundExecutable = false
        for (path in possiblePaths) {
            try {
                // Attempt to start a process with the given path to truly test if it's executable
                val testProcess = ProcessBuilder(path).start()
                testProcess.destroy() // Immediately destroy the test process if successful
                processBuilder = ProcessBuilder(path) // Initialize the actual process builder with the valid path
                foundExecutable = true
                break // Exit the loop since we've found a valid executable
            } catch (e: IOException) {
                // This path didn't work, try the next one
            }
        }

        if (!foundExecutable) {
            throw IllegalStateException("Could not find Stockfish executable in any known location.")
        }

        this.startEngineIfNeeded()
    }

    fun evaluate(fen: String, move: String): Pair<String, Evaluation> {
        if (!this.isEngineRunning) {
            this.startEngineIfNeeded()
        }

        try {
            lastUsed = Instant.now()
            sendCommand("position fen $fen moves $move")
            sendCommand("go depth 14")
            val output = getOutputUntilBestMove()

            val bestMove = extractBestMove(output)
            val evaluation = parseEvaluation(output)

            return Pair(bestMove, evaluation)
        } finally {
//            this.stopEngine()
        }
    }

    private fun extractBestMove(output: String): String {
        // using trie for this instead, keeping just in case
        val pattern = "bestmove (\\S+)".toRegex()
        val match = pattern.find(output)
        return match?.groups?.get(1)?.value ?: "error"
    }

    private fun parseEvaluation(stockfishOutput: String): Evaluation {
        var lastEvaluation: Evaluation? = null

        for (line in stockfishOutput.split("\n")) {
            if (line.contains("depth")) {
                if (line.contains("score cp")) {
                    lastEvaluation = Evaluation(extractEvaluation(line), extractPrincipalVariation(line))
                } else if (line.contains("score mate")) {
                    val mateInMoves = extractMateInMoves(line)
                    lastEvaluation = Evaluation(null, extractPrincipalVariation(line))
                }
            }
        }
//        return lastEvaluation ?: throw Exception("Failed to parse Stockfish output.")
        return lastEvaluation ?: Evaluation(0.0, "MATE")
    }

    private fun extractMateInMoves(line: String): Int? {
        val pattern = "score mate (-?\\d+)".toRegex()
        val match = pattern.find(line)
        return match?.groups?.get(1)?.value?.toIntOrNull()
    }

    private fun getOutputUntilBestMove(): String {
        val output = StringBuilder()
        while (true) {
            val line = reader.readLine() ?: break
            output.append(line).append("\n")
            if (line.contains("bestmove")) break
        }
        return output.toString()
    }


    // HELPER FUNCTIONS
    private fun extractDepth(line: String): Int? {
        val pattern = "depth (\\d+)".toRegex()
        val match = pattern.find(line)
        return match?.groups?.get(1)?.value?.toIntOrNull()
    }

    private fun extractEvaluation(line: String): Double? {
        val pattern = "score cp (-?\\d+)".toRegex()
        val match = pattern.find(line)
        return match?.groups?.get(1)?.value?.toDoubleOrNull()
    }

    private fun extractNodes(line: String): Int? {
        val pattern = "nodes (\\d+)".toRegex()
        val match = pattern.find(line)
        return match?.groups?.get(1)?.value?.toIntOrNull()
    }

    private fun extractTime(line: String): Int? {
        val pattern = "time (\\d+)".toRegex()
        val match = pattern.find(line)
        return match?.groups?.get(1)?.value?.toIntOrNull()
    }

    private fun extractPrincipalVariation(line: String): String? {
        val pattern = " pv (.+)".toRegex()
        val match = pattern.find(line)
        return match?.groups?.get(1)?.value
    }

    private fun sendCommand(command: String) {
        writer.write("$command\n")
        writer.flush()
    }

    private fun clearInitialMessages() {
        while (reader.ready()) {
            reader.readLine()
        }
    }


//  STARTUP FUNCTIONS

    private fun startEngineIfNeeded() {
        if (!this.isEngineRunning) {
            this.startEngine()
            this.isEngineRunning = true
        }
    }

    private fun startEngine() {
        process = processBuilder.start() // Start the process here
        reader = BufferedReader(InputStreamReader(process.inputStream))
        writer = BufferedWriter(OutputStreamWriter(process.outputStream))
        this.isEngineRunning = true
        clearInitialMessages()
        print("STOCKFISH STARTED")
    }

//   CLEANUP FUNCTIONS

    @PreDestroy
    fun cleanUp() {
        stopEngine()
    }

    private fun stopEngine() {
        try {
            sendCommand("quit")
            reader.close()
            writer.close()
        } catch (e: Exception) {
            // need to handle exception here
            e.printStackTrace()
        } finally {
            process.destroy()
            this.isEngineRunning = false
            print("STOCKFISH STOPPED")
        }
    }

    @Scheduled(fixedRate = 60000)
    fun checkAndStopEngine() {
        if (Duration.between(lastUsed, Instant.now()).toMinutes() >= 5 && isEngineRunning) {
            stopEngine()
        }
    }
}

data class Evaluation(val centipawns: Double?, val principalVariation: String?)

