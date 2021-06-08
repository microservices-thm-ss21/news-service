package de.thm.mni.microservices.gruppe6.news.model.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("users")
data class User(
    @Id var userId: UUID,
    var lastNewsRetrieval: LocalDateTime = LocalDateTime.MIN
)


