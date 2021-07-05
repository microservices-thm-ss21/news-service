package de.thm.mni.microservices.gruppe6.news.persistence

import de.thm.mni.microservices.gruppe6.lib.event.DomainEvent
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Document
data class DomainNews(
    val domainEvent: DomainEvent,
    @Id override val newsId: UUID = UUID.randomUUID(),
    override val timestamp: LocalDateTime = LocalDateTime.now(),
    override val newsSubject: NewsSubject = when {
            domainEvent.code.name.startsWith("USER") -> NewsSubject.USER
            domainEvent.code.name.startsWith("ISSUE") -> NewsSubject.ISSUE
            domainEvent.code.name.startsWith("PROJECT") -> NewsSubject.PROJECT
            else -> NewsSubject.ANY
        }
): News {

    override fun getMessage(): String = "${timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}: $newsSubject ${domainEvent.code.name}"

}
