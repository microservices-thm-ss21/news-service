package de.thm.mni.microservices.gruppe6.news

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NewsServiceApplication

fun main(args: Array<String>) {
    runApplication<NewsServiceApplication>(*args)
}
