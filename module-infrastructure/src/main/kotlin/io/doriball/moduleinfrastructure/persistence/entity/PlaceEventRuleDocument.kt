package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.domain.enums.DayOfWeekType
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalTime

@Document(collection = "place_event_rule")
class PlaceEventRuleDocument(
    var placeId: String,
    var name: String,
    var dayOfWeek: DayOfWeekType,
    var scheduledAt: LocalTime,
    var category: LeagueCategoryType,
    var capacity: Int?,
    var stages: List<StageDocument>,
    var entryFee: Long? = 0L,
): BaseTimeDocument() {

    @Id var id: String? = null

}