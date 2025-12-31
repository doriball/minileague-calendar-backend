package io.doriball.moduleadmin.store.domain

import io.doriball.moduleadmin.store.application.port.`in`.dto.CreateStoreEventRuleCommand
import io.doriball.modulecore.enums.DayOfWeekType
import java.time.LocalDateTime

class StoreEventRuleCreate(
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: List<StoreEventRuleStageCreate>,
) {

    companion object {
        fun from(storeId: String, command: CreateStoreEventRuleCommand): StoreEventRuleCreate = StoreEventRuleCreate(
            storeId = storeId,
            name = command.name,
            dayOfWeek = command.dayOfWeek,
            scheduledAt = command.scheduledAt,
            official = command.official,
            stages = command.stages.map { StoreEventRuleStageCreate.from(it) }
        )
    }
}