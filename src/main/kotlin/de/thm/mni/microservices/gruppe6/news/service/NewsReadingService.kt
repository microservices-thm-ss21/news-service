package de.thm.mni.microservices.gruppe6.news.service

import de.thm.mni.microservices.gruppe6.news.model.persistence.News
import de.thm.mni.microservices.gruppe6.news.model.persistence.NewsRepository
import de.thm.mni.microservices.gruppe6.news.model.persistence.User
import de.thm.mni.microservices.gruppe6.news.model.persistence.UserRepository
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

/**
 * Service used to read events from the database.
 */
@Component
class NewsReadingService(private val newsRepository: NewsRepository, private val userRepository: UserRepository) {

    fun getAllNews(params: MultiValueMap<String, String>): Flux<News>
        = newsRepository.findAll().filterNews(params)

    fun getNewsForUser(params: MultiValueMap<String, String>, userId: UUID): Flux<News> {
        return Flux.create { sink ->
            val userMono = userRepository.findById(userId)
            userMono.subscribe { user ->
                newsRepository.getNewsByTimeAfter(user.lastNewsRetrieval).filterNews(params)
                    .doOnNext(sink::next)
                user.lastNewsRetrieval = LocalDateTime.now()
                userRepository.save(user)
            }
        }
    }

    fun Flux<News>.filterNews(params: MultiValueMap<String, String>): Flux<News> {
        return this.filter { news ->
            buildPredicateList(params).fold(true) { bool, predicate ->
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
                (userFilter.isEmpty() && userFilter.contains(it.userId.toString()))
            },
            Predicate {
                (issueFilter.isEmpty() && issueFilter.contains(it.issueId.toString()))
            },
            Predicate {
                (projectFilter.isEmpty() && projectFilter.contains(it.projectId.toString()))
            },
            Predicate {
                (eventCodeFilter.isEmpty() && eventCodeFilter.contains(it.eventCode.toString()))
            }
        )
    }

}
