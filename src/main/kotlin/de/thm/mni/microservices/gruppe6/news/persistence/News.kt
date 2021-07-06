package de.thm.mni.microservices.gruppe6.news.persistence

import java.time.LocalDateTime
import java.util.*

/**
 * Interface representing News.
 */
interface News {

    val newsId: UUID
    val timestamp: LocalDateTime
    val newsSubject: NewsSubject

    /**
     * Creates a meaningful message from the News Object.
     * @return a message string.
     */
    fun getMessage(): String
}

/**
 * Enum class representing a subject of the news.
 */
enum class NewsSubject {
    ISSUE,
    PROJECT,
    USER,
    ANY
}


