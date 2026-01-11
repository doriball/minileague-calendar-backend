package io.doriball.modulecalendar.event.application.dto

import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalDateTime

class EventDto(
    val id: String,
    val name: String,
    val region: String,
    val storeName: String,
    val scheduledAt: LocalDateTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<EventStageDto>,
) {
    companion object {
        fun from(event: Event): EventDto {
            return EventDto(
                id = event.id,
                name = event.name,
                region = event.regionName,
                storeName = event.storeName,
                scheduledAt = event.scheduledAt,
                category = event.category,
                capacity = event.capacity,
                entryFee = event.entryFee,
                stages = event.stages.map { EventStageDto.from(it) }.toList(),
            )
        }
    }
}