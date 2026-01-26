package io.doriball.moduleadmin.place.domain

import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceEventRuleCommand
import io.doriball.modulecore.domain.enums.DayOfWeekType
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import java.time.LocalTime

class PlaceEventRuleUpdate(
    val id: String,
    val placeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<PlaceEventRuleStageUpdate>,
) {

    companion object {
        fun from(placeId: String, id: String, command: UpdatePlaceEventRuleCommand): PlaceEventRuleUpdate =
            PlaceEventRuleUpdate(
                id = id,
                placeId = placeId,
                name = command.name,
                dayOfWeek = command.dayOfWeek,
                scheduledAt = command.scheduledAt,
                category = command.category,
                capacity = command.capacity,
                entryFee = command.entryFee,
                stages = command.stages.map { PlaceEventRuleStageUpdate.from(it) }
            )
    }

}