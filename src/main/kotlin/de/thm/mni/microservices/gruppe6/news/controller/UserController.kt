package de.thm.mni.microservices.gruppe6.news.controller

import de.thm.mni.microservices.gruppe6.news.model.persistence.User
import de.thm.mni.microservices.gruppe6.news.model.message.UserDTO
import de.thm.mni.microservices.gruppe6.news.model.persistence.News
import de.thm.mni.microservices.gruppe6.news.service.NewsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/news")
class NewsController(@Autowired private val newsService: NewsService) {

    @GetMapping("", "/")
    fun getAllNewsWithoutDeleting(): Flux<News> = newsService.getAllNews()

    @GetMapping("/{userId}")
    fun getAllUsers(@PathVariable userId: UUID): Flux<News> = newsService.getNewsForUser(userId)

}
