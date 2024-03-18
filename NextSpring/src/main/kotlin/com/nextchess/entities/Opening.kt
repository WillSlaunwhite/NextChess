package com.nextchess.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.nextchess.converters.StringListConverter
import jakarta.persistence.*

@Entity
data class Opening(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String? = null,
    @Convert(converter = StringListConverter::class)
    @Column(name = "moves_sequence")
    val baseMoveSequence: List<String> = listOf(),
    @OneToMany(mappedBy = "opening")
    @JsonManagedReference
    val masterGames: MutableList<MasterGame>,
    @OneToMany(mappedBy = "opening")
    @JsonManagedReference
    val variations: List<Variation>,
    @Column(name = "difficulty_level")
    val difficulty: String? = null,
)
