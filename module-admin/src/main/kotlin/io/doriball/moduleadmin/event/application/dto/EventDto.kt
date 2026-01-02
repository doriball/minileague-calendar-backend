package io.doriball.moduleadmin.event.application.dto

import io.doriball.modulecore.domain.event.Event
import java.time.LocalDateTime

class EventDto(
    val id: String,
    val name: String,
    val region: String,
    val storeName: String,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val types: List<String>,
    val roundCount: Int,
    val gameCount: Int?,
) {
    companion object {
        fun from(event: Event): EventDto {
            return EventDto(
                id = event.id,
                name = event.name,
                region = event.regionName,
                storeName = event.storeName,
                scheduledAt = event.scheduledAt,
                official = event.official,
                types = event.stageTypes,
                roundCount = event.roundCount,
                gameCount = event.gameCount
            )
        }
    }

    val displayTypes: List<String> get() = types.map {
        when(it) {
            "SWISS" -> "스위스 라운드"
            "TOURNAMENT" -> "토너먼트"
            else -> it
        }
    }
}