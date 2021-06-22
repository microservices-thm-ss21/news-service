package de.thm.mni.microservices.gruppe6.news.service

import de.thm.mni.microservices.gruppe6.lib.exception.ServiceException
import de.thm.mni.microservices.gruppe6.news.persistence.News
import de.thm.mni.microservices.gruppe6.news.persistence.NewsRepository
import de.thm.mni.microservices.gruppe6.news.persistence.User
import de.thm.mni.microservices.gruppe6.news.persistence.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

/**
 * Service used to read events from the database.
 */
@Component
class NewsReadingService(private val newsRepository: NewsRepository, private val userRepository: UserRepository) {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun getAllNews(params: MultiValueMap<String, String>): Flux<News>
        = newsRepository
        .findAll()
        .log()
        .filterNews(params)
        .log()

    fun getNewsForUser(params: MultiValueMap<String, String>, userId: UUID): Flux<News> {
        return Flux.create { sink ->
            val userMono = userRepository.findById(userId).switchIfEmpty(Mono.error(ServiceException(reason = "User not found!")))
            userMono.subscribe { user ->
                newsRepository.findByTimestampBefore(user.lastNewsRetrieval).filterNews(params)
                    .doOnNext(sink::next)
                user.lastNewsRetrieval = LocalDateTime.now()
                userRepository.save(user)
            }
        }
    }

    fun Flux<News>.filterNews(params: MultiValueMap<String, String>): Flux<News> {
        return this.filter { news ->
            logger.info("Filtering ${news.newsSubject}, ${news.eventCode}")
            buildPredicateList(params).fold(true) { bool, predicate ->
                logger.info("Test: ${predicate.test(news)}")
                bool && predicate.test(news)
            }
        }
    }

    fun buildPredicateList(params: MultiValueMap<String, String>): List<Predicate<News>> {
        val userFilter = params["user"] ?: emptyList()
        val issueFilter = params["issue"] ?: emptyList()
        val projectFilter = params["project"] ?: emptyList()
        val eventCodeFilter = params["event"] ?: emptyList()
        return listOf (
            Predicate {
                (userFilter.isEmpty() || userFilter.contains(it.userId.toString()))
            },
            Predicate {
                (issueFilter.isEmpty() || issueFilter.contains(it.issueId.toString()))
            },
            Predicate {
                (projectFilter.isEmpty() || projectFilter.contains(it.projectId.toString()))
            },
            Predicate {
                (eventCodeFilter.isEmpty() || eventCodeFilter.contains(it.eventCode))
            }
        )
    }

}