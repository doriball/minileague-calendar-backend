package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalTime

@Document(collection = "place_event_rule")
class PlaceEventRuleDocument(
    val placeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val stages: List<StageDocument>,
    val entryFee: Long? = 0L,
): BaseTimeDocument() {

    @Id var id: String? = null

}