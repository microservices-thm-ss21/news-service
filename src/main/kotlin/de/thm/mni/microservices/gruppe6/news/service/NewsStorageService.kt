package de.thm.mni.microservices.gruppe6.news.service

import com.fasterxml.jackson.databind.ObjectMapper
import de.thm.mni.microservices.gruppe6.lib.event.*
import de.thm.mni.microservices.gruppe6.news.model.persistence.News
import de.thm.mni.microservices.gruppe6.news.model.persistence.NewsRepository
import io.r2dbc.postgresql.codec.Json
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NewsStorageService(private val om: ObjectMapper, private val newsRepository: NewsRepository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun storeDataEvent(dataEvent: Mono<DataEvent>) {
        dataEvent.map {
            return@map when (it) {
                is ProjectDataEvent -> News(it, Json.of(om.writeValueAsString(it)))
                is UserDataEvent -> News(it, Json.of(om.writeValueAsString(it)))
                is IssueDataEvent -> News(it, Json.of(om.writeValueAsString(it)))
                else -> error("Unexpected Event type: ${it?.javaClass}")
            }
        }.doOnNext { news ->
            newsRepository.save(news).subscribe {
                logger.debug("Storing News of type {} with data {}", it.eventCode, it.eventData)
            }.dispose()
        }.subscribe().dispose()
    }

    fun storeDomainEvent(domainEvent: Mono<DomainEvent>) {

    }
}
