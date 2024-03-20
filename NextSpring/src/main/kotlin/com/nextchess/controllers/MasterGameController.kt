package com.nextchess.controllers

import com.nextchess.services.MasterGameService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping

@CrossOrigin("*", "http://localhost")
@RequestMapping("/api/games")
class MasterGameController(masterGameService: MasterGameService) { }