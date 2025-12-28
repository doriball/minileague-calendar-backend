package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "event")
class EventDocument(
    @Id val id: String,
    val storeId: String,
    val name: String,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: MutableList<EventStageDocument> = mutableListOf(),
): BaseTimeDocument() {

}