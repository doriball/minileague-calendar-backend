package io.doriball.moduleadmin.store.domain

import io.doriball.moduleadmin.store.application.port.`in`.dto.UpdateStoreEventRuleCommand
import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalTime

class StoreEventRuleUpdate(
    val id: String,
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
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
                category = command.category,
                capacity = command.capacity,
                entryFee = command.entryFee,
                stages = command.stages.map { StoreEventRuleStageUpdate.from(it) }
            )
    }

}