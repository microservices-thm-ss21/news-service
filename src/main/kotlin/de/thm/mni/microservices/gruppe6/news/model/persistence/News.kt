package de.thm.mni.microservices.gruppe6.news.model.persistence

import de.thm.mni.microservices.gruppe6.lib.event.IssueDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.ProjectDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.UserDataEvent
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("news")
data class News(
    val newsSubject: String,
    val eventCode: String,
    val issueId: UUID? = null,
    val projectId: UUID? = null,
    val userId: UUID? = null,
    @Id val newsId: UUID? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
) {

    constructor(projectDataEvent: ProjectDataEvent): this(NewsSubject.PROJECT.name, projectDataEvent.code.name, projectId = projectDataEvent.id)
    constructor(issueDataEvent: IssueDataEvent): this(NewsSubject.ISSUE.name, issueDataEvent.code.name, issueId = issueDataEvent.id, projectId = issueDataEvent.projectId)
    constructor(userDataEvent: UserDataEvent): this(NewsSubject.USER.name, userDataEvent.code.name, userId = userDataEvent.id)

}

enum class NewsSubject {
    ISSUE,
    PROJECT,
    USER,
}

