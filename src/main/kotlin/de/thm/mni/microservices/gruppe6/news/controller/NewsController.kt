package de.thm.mni.microservices.gruppe6.news.controller

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

    /**
     * Returns all saved DataNews and DomainNews.
     * @param params Query parameters containing user, project or issue constraints.
     * @return Flux of News
     */
    @GetMapping("", "/")
    fun getAllNewsWithoutDeleting(@RequestParam params: MultiValueMap<String, String>)
        : Flux<News> = newsReadingService.getAllNews(params)

    /**
     * Returns all saved DataNews and DomainNews received after the user checked last.
     * @param params Query parameters containing user, project or issue constraints.
     * @param userId userId
     * @return Flux of News
     */
    @GetMapping("/{userId}")
    fun getNewsForUser(@RequestParam params: MultiValueMap<String, String>,
                       @PathVariable userId: UUID)
        : Flux<News> = newsReadingService.getNewsForUser(params, userId)

}
