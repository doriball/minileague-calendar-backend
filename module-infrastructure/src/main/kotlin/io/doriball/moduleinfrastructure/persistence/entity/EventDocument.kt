package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.enums.LeagueCategoryType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "event")
class EventDocument(
    val storeId: String,
    val name: String,
    val scheduledAt: LocalDateTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val stages: List<StageDocument> = listOf(),
    val entryFee: Long? = 0L,
) : BaseTimeDocument() {

    @Id var id: String? = null

}