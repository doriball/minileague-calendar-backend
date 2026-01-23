package io.doriball.moduleadmin.event.domain

import io.doriball.moduleadmin.event.application.port.`in`.dto.CreateEventCommand
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalDateTime

class EventCreate(
    val name: String,
    val placeId: String,
    val scheduledAt: LocalDateTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<EventStageCreate>,
) {

    companion object {
        fun from(command: CreateEventCommand): EventCreate =
            EventCreate(
                name = command.name,
                placeId = command.placeId,
                scheduledAt = command.scheduledAt,
                category = command.category,
                capacity = command.capacity,
                entryFee = command.entryFee,
                stages = command.stages.map { EventStageCreate.from(it) }.toList(),
            )
    }
}