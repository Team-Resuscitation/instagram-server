package com.resuscitation.instagram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class InstagramApplication

fun main(args: Array<String>) {
    runApplication<InstagramApplication>(*args)
}
