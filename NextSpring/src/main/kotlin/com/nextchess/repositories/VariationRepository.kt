package com.nextchess.repositories

import com.nextchess.entities.Opening
import com.nextchess.entities.Variation
import org.springframework.data.jpa.repository.JpaRepository

interface VariationRepository : JpaRepository<Variation, Long> {
    fun findByName(name: String): Variation?
    fun findByOpening(opening: Opening): List<Variation> = listOf()
}