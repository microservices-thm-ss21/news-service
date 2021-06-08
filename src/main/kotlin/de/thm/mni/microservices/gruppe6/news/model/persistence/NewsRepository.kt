package de.thm.mni.microservices.gruppe6.news.model.persistence

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

@Repository
interface NewsRepository: ReactiveCrudRepository<News, UUID> {

    @Query("SELECT * from news n where n.time >= $1")
    fun getNewsByTimeAfter(time: LocalDateTime): Flux<News>

}
