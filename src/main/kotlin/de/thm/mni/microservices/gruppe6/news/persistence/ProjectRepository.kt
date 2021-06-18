package de.thm.mni.microservices.gruppe6.news.persistence

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.*

interface ProjectRepository: ReactiveMongoRepository<Project, UUID>
