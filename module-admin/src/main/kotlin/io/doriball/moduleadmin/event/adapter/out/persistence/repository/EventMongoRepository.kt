package io.doriball.moduleadmin.event.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface EventMongoRepository : MongoRepository<EventDocument, String> {
}