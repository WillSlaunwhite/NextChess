package com.nextchess.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val username: String,
    val password: String,
    val role: String,
    val email: String,
    @Column(name = "registration_date")
    val registrationDate: LocalDateTime,
    @Column(name = "last_login")
    val lastLogin: LocalDateTime,
    val preferences: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (role != other.role) return false
        if (email != other.email) return false
        if (registrationDate != other.registrationDate) return false
        if (lastLogin != other.lastLogin) return false
        return preferences == other.preferences
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + registrationDate.hashCode()
        result = 31 * result + lastLogin.hashCode()
        result = 31 * result + preferences.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, username='$username', role='$role', email='$email', registrationDate=$registrationDate, lastLogin=$lastLogin)"
    }
}