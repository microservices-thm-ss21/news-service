package de.thm.mni.microservices.gruppe6.news.persistence

import de.thm.mni.microservices.gruppe6.lib.event.IssueDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.ProjectDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.UserDataEvent
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Document
data class News(
    val newsSubject: String,
    val eventCode: String,
    val event: Serializable,
    val issueId: UUID? = null,
    val projectId: UUID? = null,
    val userId: UUID? = null,
    @Id val newsId: UUID = UUID.randomUUID(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
) {

    constructor(projectDataEvent: ProjectDataEvent): this(NewsSubject.PROJECT.name, projectDataEvent.code.name, projectDataEvent, projectId = projectDataEvent.id)
    constructor(issueDataEvent: IssueDataEvent): this(NewsSubject.ISSUE.name, issueDataEvent.code.name, issueDataEvent, issueId = issueDataEvent.id, projectId = issueDataEvent.projectId)
    constructor(userDataEvent: UserDataEvent): this(NewsSubject.USER.name, userDataEvent.code.name, userDataEvent, userId = userDataEvent.id)

}

enum class NewsSubject {
    ISSUE,
    PROJECT,
    USER,
}
