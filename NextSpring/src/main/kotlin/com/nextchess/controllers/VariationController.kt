package com.nextchess.controllers

import com.nextchess.services.VariationService
import com.nextchess.entities.Variation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
@RequestMapping("/api/variations")
class VariationController(private val variationService: VariationService) {

    @GetMapping("/{variationName}")
    fun getByName(@PathVariable variationName: String): ResponseEntity<Variation> {
        val formattedVariationName = variationName.replace("-", " ")
        return variationService.getVariationByName(formattedVariationName)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/opening/{openingName}")
    fun getByOpening(@PathVariable openingName: String): ResponseEntity<List<Variation>> {
        val formattedOpeningName = openingName.replace("-", " ")
        return ResponseEntity.ok(variationService.getVariationByOpening(formattedOpeningName))
    }
}