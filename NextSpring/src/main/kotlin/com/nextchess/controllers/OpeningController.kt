package com.nextchess.controllers

import com.nextchess.data.DetailedOpeningDTO
import com.nextchess.data.OpeningDTO
import com.nextchess.services.OpeningService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/api/openings")
class OpeningController(private val openingService: OpeningService) {

    @GetMapping("/{openingName}/start")
    fun startOpening(@PathVariable openingName: String): ResponseEntity<OpeningDTO> {
        val formattedOpeningName = openingName.replace("-", " ")
        return openingService.getOpeningByName(formattedOpeningName)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{openingName}/start/detail")
    fun startOpeningDetailed(@PathVariable openingName: String): ResponseEntity<DetailedOpeningDTO> {
        val formattedOpeningName = openingName.replace("-", " ")
        return openingService.getDetailedOpeningByName(formattedOpeningName)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/")
    fun getAllOpenings(): ResponseEntity<List<OpeningDTO>> {
        return ResponseEntity.ok(openingService.getOpenings())
    }

}