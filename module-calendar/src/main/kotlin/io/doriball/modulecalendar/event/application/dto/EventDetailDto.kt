package io.doriball.modulecalendar.event.application.dto

import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import java.time.LocalDateTime

class EventDetailDto(
    val id: String,
    val name: String,
    val region: String,
    val place: String,
    val scheduledAt: LocalDateTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long? = 0L,
    val stages: List<EventStageDto>,
) {

    companion object {
        fun from(event: Event): EventDetailDto {
            return EventDetailDto(
                id = event.id,
                name = event.name,
                region = event.regionName,
                place = event.placeName,
                scheduledAt = event.scheduledAt,
                category = event.category,
                capacity = event.capacity,
                entryFee = event.entryFee,
                stages = event.stages.map { EventStageDto.from(it) }.toList(),
            )
        }
    }

}
