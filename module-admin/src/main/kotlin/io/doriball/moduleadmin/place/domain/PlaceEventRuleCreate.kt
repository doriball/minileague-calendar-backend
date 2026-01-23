package io.doriball.moduleadmin.place.domain

import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceEventRuleCommand
import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalTime

class PlaceEventRuleCreate(
    val placeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<PlaceEventRuleStageCreate>,
) {

    companion object {
        fun from(placeId: String, command: CreatePlaceEventRuleCommand): PlaceEventRuleCreate = PlaceEventRuleCreate(
            placeId = placeId,
            name = command.name,
            dayOfWeek = command.dayOfWeek,
            scheduledAt = command.scheduledAt,
            category = command.category,
            capacity = command.capacity,
            entryFee = command.entryFee,
            stages = command.stages.map { PlaceEventRuleStageCreate.from(it) }
        )
    }
}