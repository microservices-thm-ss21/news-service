package de.thm.mni.microservices.gruppe6.news.service

import com.fasterxml.jackson.databind.ObjectMapper
import de.thm.mni.microservices.gruppe6.lib.event.*
import de.thm.mni.microservices.gruppe6.news.persistence.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NewsStorageService(private val om: ObjectMapper, private val dataNewsRepository: DataNewsRepository, private val domainNewsRepository: DomainNewsRepository) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun storeDataEvent(dataEvent: Mono<DataEvent>) {
        dataEvent.map {
            DataNews(it)
        }.flatMap(dataNewsRepository::save).subscribe().dispose()
    }

    fun storeDomainEvent(domainEvent: Mono<DomainEvent>) {
        domainEvent.map {
            DomainNews(it)
        }.flatMap(domainNewsRepository::save).subscribe().dispose()
    }
}
