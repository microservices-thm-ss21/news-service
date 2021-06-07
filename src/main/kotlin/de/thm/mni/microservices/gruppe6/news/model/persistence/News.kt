package de.thm.mni.microservices.gruppe6.news.model.persistence

import io.r2dbc.postgresql.codec.Json
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table
data class News(

    /* Zusätzlich:
     * IssueId Nullable
     * ProjectId nullable
     * userId nullable
     * eventcode not null
     */
    @Id private val newsId: UUID,
    private val timestamp: LocalDateTime,
    private val eventData: Json
)
