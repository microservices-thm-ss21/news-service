package de.thm.mni.microservices.gruppe6.news.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document
data class User(
    @Id var userId: UUID,
    var lastNewsRetrieval: Date
)


