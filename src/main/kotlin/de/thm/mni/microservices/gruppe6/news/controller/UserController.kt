package de.thm.mni.microservices.gruppe6.news.controller

import de.thm.mni.microservices.gruppe6.news.model.persistence.News
import de.thm.mni.microservices.gruppe6.news.service.NewsRetrievalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.util.*

@RestController
@RequestMapping("/api/news")
class NewsController(@Autowired private val newsRetrievalService: NewsRetrievalService) {

    @GetMapping("", "/")
    fun getAllNewsWithoutDeleting(): Flux<News> = newsRetrievalService.getAllNews()

    @GetMapping("/{userId}")
    fun getAllUsers(@PathVariable userId: UUID): Flux<News> = newsRetrievalService.getNewsForUser(userId)

}
