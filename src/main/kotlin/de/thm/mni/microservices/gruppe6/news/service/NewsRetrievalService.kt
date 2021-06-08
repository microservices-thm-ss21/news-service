package de.thm.mni.microservices.gruppe6.news.service

import de.thm.mni.microservices.gruppe6.news.model.persistence.News
import de.thm.mni.microservices.gruppe6.news.model.persistence.NewsRepository
import de.thm.mni.microservices.gruppe6.news.model.persistence.User
import de.thm.mni.microservices.gruppe6.news.model.persistence.UserRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.*

@Component
class NewsRetrievalService(private val newsRepository: NewsRepository, private val userRepository: UserRepository) {

    fun getAllNews(): Flux<News> = newsRepository.findAll()

    fun getNewsForUser(userId: UUID): Flux<News> {
        val user = userRepository.findById(userId)
        user.map {
            it.lastNewsRetrieval = LocalDateTime.now()
            it
        }.subscribe {
            userRepository.save(it).subscribe().dispose()
        }
        return user.flatMapMany {
            newsRepository.getNewsByTimeAfter(it.lastNewsRetrieval)
        }
    }

}
