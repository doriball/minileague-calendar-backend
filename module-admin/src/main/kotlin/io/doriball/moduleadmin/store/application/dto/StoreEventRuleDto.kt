package io.doriball.moduleadmin.store.application.dto

import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.enums.DayOfWeekType
import java.time.LocalTime

class StoreEventRuleDto(
    val id: String,
    val name: String,
    val storeId: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val official: Boolean,
    val entryFee: Long?,
    val stages: List<StoreEventRuleStageDto>,
) {

    companion object {
        fun from(storeEventRule: StoreEventRule): StoreEventRuleDto = StoreEventRuleDto(
            id = storeEventRule.id,
            name = storeEventRule.name,
            storeId = storeEventRule.storeId,
            dayOfWeek = storeEventRule.dayOfWeek,
            scheduledAt = storeEventRule.scheduledAt,
            official = storeEventRule.official,
            entryFee = storeEventRule.entryFee,
            stages = storeEventRule.stages.map { StoreEventRuleStageDto.from(it) },
        )
    }

}