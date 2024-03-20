package com.nextchess.services

import com.nextchess.entities.Opening
import com.nextchess.entities.Variation
import com.nextchess.repositories.OpeningRepository
import com.nextchess.repositories.VariationRepository
import org.springframework.stereotype.Service

@Service
class VariationService(private val variationRepo: VariationRepository, private val openingRepo: OpeningRepository) {

    fun getVariationByName(name: String): Variation? {
        return variationRepo.findByName(name)
    }

    fun getVariationByOpening(name: String): List<Variation> {
        val opening: Opening? = openingRepo.findByName(name)
        return when {
            opening != null -> variationRepo.findByOpening(opening)
            else -> listOf()
        }
    }
}