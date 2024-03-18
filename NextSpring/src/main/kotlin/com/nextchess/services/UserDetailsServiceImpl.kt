package com.nextchess.services

import com.nextchess.entities.UserDetailsImpl
import com.nextchess.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
) : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
       val user = userRepository.findByUsername(username)

        return UserDetailsImpl.build(user)
    }
}