package de.thm.mni.microservices.gruppe6.news.service

import de.thm.mni.microservices.gruppe6.lib.exception.ServiceException
import de.thm.mni.microservices.gruppe6.news.persistence.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*
import java.util.function.Predicate

/**
 * Service used to read events from the database.
 */
@Component
class NewsReadingService(private val domainNewsRepository: DomainNewsRepository,
                         private val dataNewsRepository: DataNewsRepository,
                         private val userRepository: UserRepository) {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun getAllNews(params: MultiValueMap<String, String>): Flux<News>
        = Flux.concat(dataNewsRepository.findAll(), domainNewsRepository.findAll())
        .log()

    fun getNewsForUser(params: MultiValueMap<String, String>, userId: UUID): Flux<News> {
        return Flux.create { sink ->
            val userMono = userRepository
                .findById(userId)
                .switchIfEmpty(Mono.error(ServiceException(reason = "User not found!")))
            userMono.subscribe { user ->
                Flux.concat(
                    dataNewsRepository.findByTimestampAfter(user.lastNewsRetrieval),
                    domainNewsRepository.findByTimestampAfter(user.lastNewsRetrieval)
                ).log().subscribe(sink::next).dispose()
                user.lastNewsRetrieval = LocalDateTime.now()
                userRepository.save(user).subscribe().dispose()
            }
        }
    }

//    fun Flux<DataNews>.filterNews(params: MultiValueMap<String, String>): Flux<DataNews> {
//        return this.filter { news ->
//            logger.info("Filtering ${news.newsSubject}, ${news.dataEvent.id}")
//            buildPredicateList(params).fold(true) { bool, predicate ->
//                logger.info("Test: ${predicate.test(news)}")
//                bool && predicate.test(news)
//            }
//        }
//    }

//    fun buildPredicateList(params: MultiValueMap<String, String>): List<Predicate<DataNews>> {
//        val userFilter = params["user"] ?: emptyList()
//        val issueFilter = params["issue"] ?: emptyList()
//        val projectFilter = params["project"] ?: emptyList()
//        val eventCodeFilter = params["event"] ?: emptyList()
//        return listOf (
//            Predicate {
//                (userFilter.isEmpty() || userFilter.contains(it.userId.toString()))
//            },
//            Predicate {
//                (issueFilter.isEmpty() || issueFilter.contains(it.issueId.toString()))
//            },
//            Predicate {
//                (projectFilter.isEmpty() || projectFilter.contains(it.projectId.toString()))
//            },
//            Predicate {
//                (eventCodeFilter.isEmpty() || eventCodeFilter.contains(it.eventCode))
//            }
//        )
//    }

}
