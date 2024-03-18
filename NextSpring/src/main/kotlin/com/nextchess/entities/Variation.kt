package com.nextchess.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.nextchess.converters.StringListConverter
import jakarta.persistence.*

@Entity
@Table(name = "variations")
data class Variation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    @Convert(converter = StringListConverter::class)
    @Column(name = "moves_sequence")
    val movesSequence: List<String> = listOf(),
    @ManyToOne @JoinColumn(name = "opening_id")
    @JsonBackReference
    val opening: Opening
)