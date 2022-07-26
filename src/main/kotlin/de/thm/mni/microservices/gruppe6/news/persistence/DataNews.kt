package de.thm.mni.microservices.gruppe6.news.persistence

import de.thm.mni.microservices.gruppe6.lib.event.DataEvent
import de.thm.mni.microservices.gruppe6.lib.event.IssueDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.ProjectDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.UserDataEvent
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Document class holding DataNews, i.e. newly created issues, users or projects.
 * @param dataEvent the DataEvent object
 * @param newsId UUID of the News object
 * @param timestamp timestamp of the news
 * @param newsSubject subject of the news: Is a Project, Issue or User in question.
 */
@Document
data class DataNews(
    val dataEvent: DataEvent,
    @Id override val newsId: UUID = UUID.randomUUID(),
    override val timestamp: LocalDateTime = LocalDateTime.now(),
    override var newsSubject: NewsSubject = when (dataEvent) {
            is ProjectDataEvent -> NewsSubject.PROJECT
            is IssueDataEvent -> NewsSubject.ISSUE
            is UserDataEvent -> NewsSubject.USER
            else -> NewsSubject.ANY
        }
): News {
    override fun getMessage() = "${timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}: ${newsSubject.name} ${dataEvent.id} is ${dataEvent.code}"
}
