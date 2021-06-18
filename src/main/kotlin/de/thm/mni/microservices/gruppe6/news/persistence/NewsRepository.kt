package de.thm.mni.microservices.gruppe6.news.persistence

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

@Repository
interface NewsRepository: ReactiveMongoRepository<News, UUID> {

    /**
     * Fetches all news from the database whose timestamp is after the given LocalDateTime.
     */
    fun findByTimestampBefore(time: LocalDateTime): Flux<News>

}
