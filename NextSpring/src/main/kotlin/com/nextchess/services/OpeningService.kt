package com.nextchess.services

import com.nextchess.repositories.OpeningRepository
import com.nextchess.data.DetailedOpeningDTO
import com.nextchess.data.OpeningDTO
import com.nextchess.repositories.MasterGameRepository
import com.nextchess.utils.convertToDetailedOpeningDTO
import com.nextchess.utils.convertToOpeningDTO
import org.springframework.stereotype.Service

@Service
class OpeningService(
    private val openingRepository: OpeningRepository,
    private val masterGameRepo: MasterGameRepository
) {
    fun getOpeningByName(name: String): OpeningDTO? {
        val opening = openingRepository.findByName(name)
        return if (opening != null) convertToOpeningDTO(opening) else null
    }


    fun getDetailedOpeningByName(name: String): DetailedOpeningDTO? {
        val opening = openingRepository.findByName(name)

        return if (opening != null) {
            val games = masterGameRepo.findByOpening(opening)
            opening.masterGames.addAll(games)
            convertToDetailedOpeningDTO(opening)
        } else {
            null
        }
    }

    fun getOpenings(): List<OpeningDTO> {
        return openingRepository.findAll().map { convertToOpeningDTO(it) }
    }
}