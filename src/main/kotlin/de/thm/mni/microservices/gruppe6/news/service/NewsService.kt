package de.thm.mni.microservices.gruppe6.news.service

import de.thm.mni.microservices.gruppe6.news.model.persistence.News
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.*

@Component
class NewsService {

    fun getAllNews(): Flux<News> {
        TODO("Not yet implemented")
    }

    fun getNewsForUser(userId: UUID): Flux<News> {
        TODO("Not yet implemented")
    }

}
