package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "event")
class EventDocument(
    val storeId: String,
    val name: String,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: List<StageDocument> = listOf(),
    val entryFee: Long?,
) : BaseTimeDocument() {

    @Id var id: String? = null

}