package io.doriball.moduleadmin.store.domain

import io.doriball.moduleadmin.store.application.port.`in`.dto.CreateStoreEventRuleCommand
import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalTime

class StoreEventRuleCreate(
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<StoreEventRuleStageCreate>,
) {

    companion object {
        fun from(storeId: String, command: CreateStoreEventRuleCommand): StoreEventRuleCreate = StoreEventRuleCreate(
            storeId = storeId,
            name = command.name,
            dayOfWeek = command.dayOfWeek,
            scheduledAt = command.scheduledAt,
            category = command.category,
            capacity = command.capacity,
            entryFee = command.entryFee,
            stages = command.stages.map { StoreEventRuleStageCreate.from(it) }
        )
    }
}