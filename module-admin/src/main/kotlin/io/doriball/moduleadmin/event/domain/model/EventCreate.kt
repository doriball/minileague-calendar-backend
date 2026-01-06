package io.doriball.moduleadmin.event.domain.model

import io.doriball.moduleadmin.event.application.port.`in`.dto.CreateEventCommand
import java.time.LocalDateTime

class EventCreate(
    val name: String,
    val storeId: String,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val entryFee: Long?,
    val stages: List<EventStageCreate>,
) {

    companion object {
        fun from(command: CreateEventCommand): EventCreate =
            EventCreate(
                name = command.name,
                storeId = command.storeId,
                scheduledAt = command.scheduledAt,
                official = command.official,
                entryFee = command.entryFee,
                stages = command.stages.map { EventStageCreate.from(it) }.toList(),
            )
    }
}