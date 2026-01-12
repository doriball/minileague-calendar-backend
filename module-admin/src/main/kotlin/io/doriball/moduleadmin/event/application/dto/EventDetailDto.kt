package io.doriball.moduleadmin.event.application.dto

import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalDateTime

class EventDetailDto(
    val id: String,
    val name: String,
    val region: String,
    val storeId: String,
    val storeName: String,
    val scheduledAt: LocalDateTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<EventDetailStageDto>
) {

    companion object {
        fun from(event: Event): EventDetailDto {
            return EventDetailDto(
                id = event.id,
                name = event.name,
                region = event.regionName,
                storeId = event.storeId,
                storeName = event.storeName,
                scheduledAt = event.scheduledAt,
                category = event.category,
                capacity = event.capacity,
                entryFee = event.entryFee,
                stages = event.stages.map { EventDetailStageDto.from(it) }.toList()
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

}