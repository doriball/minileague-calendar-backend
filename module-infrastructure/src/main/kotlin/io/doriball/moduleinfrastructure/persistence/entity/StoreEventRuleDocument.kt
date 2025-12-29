package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.enums.DayOfWeekType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "store_event_rule")
class StoreEventRuleDocument(
    @Id val id: String,
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: List<StageDocument>,
): BaseTimeDocument() {
}