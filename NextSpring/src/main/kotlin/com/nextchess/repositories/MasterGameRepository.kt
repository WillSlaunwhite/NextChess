package com.nextchess.repositories

import com.nextchess.entities.MasterGame
import com.nextchess.entities.Opening
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MasterGameRepository : JpaRepository<MasterGame, Long> {
    @Deprecated("Deprecated in Java")
    override fun getById(id: Long): MasterGame
    fun findByOpening(opening: Opening): List<MasterGame>

}