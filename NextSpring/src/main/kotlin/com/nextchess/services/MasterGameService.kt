package com.nextchess.services

import com.nextchess.repositories.MasterGameRepository
import org.springframework.stereotype.Service

@Service
class MasterGameService(private val masterGameRepo: MasterGameRepository) {
    fun getMasterGamesByOpeningSequence(moveSequence: List<String>) {
        val games = masterGameRepo.findAll()
    }
}