package de.thm.mni.microservices.gruppe6.news.persistence

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@NoRepositoryBean
interface NewsRepository<T: News, ID>: ReactiveMongoRepository<T, ID> {

    /**
     * Fetches all news from the database whose timestamp is after the given LocalDateTime.
     */
    fun findByTimestampAfter(time: Date): Flux<T>

    fun findByNewsSubject(newsSubject: NewsSubject): Flux<T>

}

@Repository
interface DataNewsRepository: NewsRepository<DataNews, UUID>

@Repository
interface DomainNewsRepository: NewsRepository<DomainNews, UUID>
