package de.thm.mni.microservices.gruppe6.news.persistence

import java.time.LocalDateTime
import java.util.*

interface News {

    val newsId: UUID
    val timestamp: LocalDateTime
    val newsSubject: NewsSubject

    fun getMessage(): String
}

enum class NewsSubject {
    ISSUE,
    PROJECT,
    USER,
    ANY
}


