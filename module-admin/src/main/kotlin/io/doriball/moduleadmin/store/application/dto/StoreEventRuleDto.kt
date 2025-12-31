package io.doriball.moduleadmin.store.application.dto

import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.enums.DayOfWeekType
import java.time.LocalDateTime

class StoreEventRuleDto(
    val id: String,
    val name: String,
    val storeId: String,
    val dayOfWeek: DayOfWeekType,
    val scheduleAt: LocalDateTime,
    val official: Boolean,
    val stages: List<StoreEventRuleStageDto>,
) {

    companion object {
        fun from(storeEventRule: StoreEventRule): StoreEventRuleDto = StoreEventRuleDto(
            id = storeEventRule.id,
            name = storeEventRule.name,
            storeId = storeEventRule.storeId,
            dayOfWeek = storeEventRule.dayOfWeek,
            scheduleAt = storeEventRule.scheduledAt,
            official = storeEventRule.official,
            stages = storeEventRule.stages.map { StoreEventRuleStageDto.from(it) },
        )
    }

}