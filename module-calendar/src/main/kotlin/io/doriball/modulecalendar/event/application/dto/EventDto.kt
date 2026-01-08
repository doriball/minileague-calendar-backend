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
                category = event.category,
                types = event.stageTypes,
                roundCount = event.roundCount,
                gameCount = event.gameCount
            )
        }
    }
}