package io.doriball.moduleadmin.event.domain

import io.doriball.moduleadmin.event.application.port.`in`.dto.UpdateEventCommand
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import java.time.LocalDateTime

class EventUpdate(
    val id: String,
    val name: String,
    val placeId: String,
    val scheduledAt: LocalDateTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<EventStageUpdate>,
) {

    companion object {
        fun from(eventId: String, command: UpdateEventCommand): EventUpdate =
            EventUpdate(
                id = eventId,
                name = command.name,
                placeId = command.placeId,
                scheduledAt = command.scheduledAt,
                category = command.category,
                capacity = command.capacity,
                entryFee = command.entryFee,
                stages = command.stages.map { EventStageUpdate.from(it) }.toList(),
            )
    }

}