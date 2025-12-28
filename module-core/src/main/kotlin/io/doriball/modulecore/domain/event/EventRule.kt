package io.doriball.modulecore.domain.event

import io.doriball.modulecore.enums.DayOfWeekType
import java.time.LocalDateTime

class EventRule(
    val id: String,
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val official: Boolean,
    val stages: MutableList<EventStage>,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
}