package com.nextchess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@PropertySource("classpath:secrets.properties")
@EntityScan("com.nextchess.entities")
@EnableJpaRepositories("com.nextchess.repositories")
@ComponentScan(
	"com.nextchess.threading",
	"com.nextchess.controllers",
	"com.nextchess.security",
	"com.nextchess.services",
	"com.nextchess.repositories"
)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
