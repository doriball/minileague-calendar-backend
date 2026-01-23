package io.doriball.moduleadmin.place.application.dto

import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalTime

class PlaceEventRuleDto(
    val id: String,
    val name: String,
    val placeId: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<PlaceEventRuleStageDto>,
) {

    companion object {
        fun from(placeEventRule: PlaceEventRule): PlaceEventRuleDto = PlaceEventRuleDto(
            id = placeEventRule.id,
            name = placeEventRule.name,
            placeId = placeEventRule.placeId,
            dayOfWeek = placeEventRule.dayOfWeek,
            scheduledAt = placeEventRule.scheduledAt,
            category = placeEventRule.category,
            capacity = placeEventRule.capacity,
            entryFee = placeEventRule.entryFee,
            stages = placeEventRule.stages.map { PlaceEventRuleStageDto.from(it) },
        )
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