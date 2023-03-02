package com.github.simonhauck.budgetturtle.server.core.db

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoDatabase
import mu.KotlinLogging
import org.litote.kmongo.KMongo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoConfig {
    private val log = KotlinLogging.logger {}

    @Bean
    fun mongoClient(): MongoDatabase {
        val dataBaseName = "admin"
        val userName = "root"
        log.info { "Connecting to database $dataBaseName with user $userName" }

        val config =
            MongoClientSettings.builder()
                .credential(
                    MongoCredential.createCredential(
                        userName,
                        dataBaseName,
                        "example".toCharArray()
                    )
                )
        return KMongo.createClient(settings = config.build()).getDatabase("budget-turtle")
    }
}
