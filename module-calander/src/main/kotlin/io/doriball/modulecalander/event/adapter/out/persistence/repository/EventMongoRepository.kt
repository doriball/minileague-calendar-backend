package io.doriball.modulecalander.event.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface EventMongoRepository : MongoRepository<EventDocument, String> {

    fun findByScheduledAtBetween(start: LocalDateTime, end: LocalDateTime): List<EventDocument>

    fun findByScheduledAtBetweenAndStoreIdIn(start: LocalDateTime, end: LocalDateTime, storeIds: List<String>): List<EventDocument>

}