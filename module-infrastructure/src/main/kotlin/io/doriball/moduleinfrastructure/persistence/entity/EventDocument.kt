package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.domain.enums.LeagueCategoryType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "event")
class EventDocument(
    var placeId: String,
    var name: String,
    var scheduledAt: LocalDateTime,
    var category: LeagueCategoryType,
    var capacity: Int?,
    var stages: List<StageDocument> = listOf(),
    var entryFee: Long? = 0L,
) : BaseTimeDocument() {

    @Id var id: String? = null

}