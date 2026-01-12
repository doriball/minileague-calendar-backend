package io.doriball.moduleadmin.event.application.dto

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

    val displayCategory: String
        get() = when (category) {
            LeagueCategoryType.OFFICIAL -> "공인"
            LeagueCategoryType.UNOFFICIAL -> "비공인"
            LeagueCategoryType.EVENT -> "이벤트"
            LeagueCategoryType.KOREAN_LEAGUE -> "코리안 리그"
        }

    val displayCategoryBadge: String
        get() = when (category) {
            LeagueCategoryType.OFFICIAL -> "bg-success"
            LeagueCategoryType.UNOFFICIAL -> "bg-secondary"
            LeagueCategoryType.EVENT -> "bg-warning"
            LeagueCategoryType.KOREAN_LEAGUE -> "bg-primary"
        }

    val displayTypes: List<String> get() = types.map {
        when(it) {
            "SWISS" -> "스위스 라운드"
            "TOURNAMENT" -> "토너먼트"
            else -> it
        }
    }
}