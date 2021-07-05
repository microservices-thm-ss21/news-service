package de.thm.mni.microservices.gruppe6.news.controller

import de.thm.mni.microservices.gruppe6.news.persistence.DataNews
import de.thm.mni.microservices.gruppe6.news.persistence.News
import de.thm.mni.microservices.gruppe6.news.service.NewsReadingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.util.*

@RestController
@RequestMapping("/api/news")
@CrossOrigin
class NewsController(@Autowired private val newsReadingService: NewsReadingService) {

    @GetMapping("", "/")
    fun getAllNewsWithoutDeleting(@RequestParam params: MultiValueMap<String, String>)
        : Flux<News> = newsReadingService.getAllNews(params)

    @GetMapping("/{userId}")
    fun getAllUsers(@RequestParam params: MultiValueMap<String, String>,
                    @PathVariable userId: UUID)
        : Flux<News> = newsReadingService.getNewsForUser(params, userId)

}
