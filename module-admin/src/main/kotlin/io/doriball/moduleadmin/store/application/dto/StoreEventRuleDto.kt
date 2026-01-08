package io.doriball.moduleadmin.store.application.dto

import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalTime

class StoreEventRuleDto(
    val id: String,
    val name: String,
    val storeId: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    val stages: List<StoreEventRuleStageDto>,
) {

    companion object {
        fun from(storeEventRule: StoreEventRule): StoreEventRuleDto = StoreEventRuleDto(
            id = storeEventRule.id,
            name = storeEventRule.name,
            storeId = storeEventRule.storeId,
            dayOfWeek = storeEventRule.dayOfWeek,
            scheduledAt = storeEventRule.scheduledAt,
            category = storeEventRule.category,
            capacity = storeEventRule.capacity,
            entryFee = storeEventRule.entryFee,
            stages = storeEventRule.stages.map { StoreEventRuleStageDto.from(it) },
        )
    }

    val displayCategory: String
        get() = when (category) {
            LeagueCategoryType.OFFICIAL -> "공인"
            LeagueCategoryType.PRIVATE -> "사설"
            LeagueCategoryType.EVENT -> "이벤트"
            LeagueCategoryType.KOREAN_LEAGUE -> "코리안 리그"
        }

    val displayCategoryBadge: String
        get() = when (category) {
            LeagueCategoryType.OFFICIAL -> "bg-success"
            LeagueCategoryType.PRIVATE -> "bg-secondary"
            LeagueCategoryType.EVENT -> "bg-warning"
            LeagueCategoryType.KOREAN_LEAGUE -> "bg-primary"
        }

}