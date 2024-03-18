package com.nextchess.repositories

import com.nextchess.entities.Opening
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OpeningRepository : JpaRepository<Opening, Long> {
    fun findByName(name: String): Opening?
}