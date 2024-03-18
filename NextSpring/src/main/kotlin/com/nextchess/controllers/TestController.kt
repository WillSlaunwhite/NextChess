package com.nextchess.controllers

import com.nextchess.data.AuthenticationRequest
import com.nextchess.data.AuthenticationResponse
import com.nextchess.security.JwtUtil
import com.nextchess.services.UserDetailsServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
@CrossOrigin("*", "http://localhost")
class TestController() {

    @GetMapping("test")
    fun test(): ResponseEntity<String> {
        return ResponseEntity.ok("Backend works.")
    }
}