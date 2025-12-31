package io.doriball.moduleadmin.store.domain

import io.doriball.moduleadmin.store.application.port.`in`.dto.UpdateStoreEventRuleCommand
import io.doriball.modulecore.enums.DayOfWeekType
import java.time.LocalDateTime

class StoreEventRuleUpdate(
    val id: String,
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: List<StoreEventRuleStageUpdate>,
) {

    companion object {
        fun from(storeId: String, id: String, command: UpdateStoreEventRuleCommand): StoreEventRuleUpdate =
            StoreEventRuleUpdate(
                id = id,
                storeId = storeId,
                name = command.name,
                dayOfWeek = command.dayOfWeek,
                scheduledAt = command.scheduledAt,
                official = command.official,
                stages = command.stages.map { StoreEventRuleStageUpdate.from(it) }
            )
    }

}