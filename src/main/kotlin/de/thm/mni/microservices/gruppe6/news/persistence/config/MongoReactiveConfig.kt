package de.thm.mni.microservices.gruppe6.news.persistence.config

import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean

/**
 * Configuration for reactive mongo db repositories + mongo client.
 */
@EnableReactiveMongoRepositories
class MongoReactiveConfig: AbstractReactiveMongoConfiguration() {

    @Bean
    fun mongoClient(): MongoClient {
        return MongoClients.create()
    }

    override fun getDatabaseName(): String {
        return "reactive"
    }
}
