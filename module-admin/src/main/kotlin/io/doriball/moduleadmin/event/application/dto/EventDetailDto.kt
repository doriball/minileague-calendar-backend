package io.doriball.moduleadmin.event.application.dto

import io.doriball.modulecore.domain.event.Event
import java.time.LocalDateTime

class EventDetailDto(
    val id: String,
    val name: String,
    val region: String,
    val storeName: String,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: List<EventDetailStageDto>
) {

    companion object {
        fun from(event: Event): EventDetailDto {
            return EventDetailDto(
                id = event.id,
                name = event.name,
                region = event.regionName,
                storeName = event.storeName,
                scheduledAt = event.scheduledAt,
                official = event.official,
                stages = event.stages.map { EventDetailStageDto.from(it) }.toList()
            )
        }
    }

}