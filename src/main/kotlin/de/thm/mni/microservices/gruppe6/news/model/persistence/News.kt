package de.thm.mni.microservices.gruppe6.news.model.persistence

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import de.thm.mni.microservices.gruppe6.lib.event.IssueDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.ProjectDataEvent
import de.thm.mni.microservices.gruppe6.lib.event.UserDataEvent
import io.r2dbc.postgresql.codec.Json
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("news")
data class News(
    val eventCode: String?,
    @JsonSerialize(using = JsonDeserializer::class)
    val eventData: Json?,
    val issueId: UUID? = null,
    val projectId: UUID? = null,
    val userId: UUID? = null,
    @Id val newsId: UUID? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    ) {

    constructor(projectDataEvent: ProjectDataEvent, json: Json): this(projectDataEvent.code.name, json, projectId = projectDataEvent.id)
    constructor(issueDataEvent: IssueDataEvent, json: Json): this(issueDataEvent.code.name, json, issueId = issueDataEvent.id, projectId = issueDataEvent.projectId)
    constructor(userDataEvent: UserDataEvent, json: Json): this(userDataEvent.code.name, json, userId = userDataEvent.id)

}

class JsonDeserializer: StdSerializer<Json>(Json::class.java) {
    override fun serialize(obj: Json, gen: JsonGenerator, prov: SerializerProvider) {
        gen.writeString(obj.asString())
    }
}


