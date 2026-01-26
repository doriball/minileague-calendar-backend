package io.doriball.modulecalendar.place.application.dto

import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import java.time.LocalTime

class StoreEventRuleDto(
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long? = 0L,
    val types: List<String>,
    val roundCount: Int,
    val gameCount: Int?,
) {

    companion object {
        fun from(rule: PlaceEventRule): StoreEventRuleDto = StoreEventRuleDto(
            scheduledAt = rule.scheduledAt,
            category = rule.category,
            capacity = rule.capacity,
            entryFee = rule.entryFee,
            types = rule.stageTypes,
            roundCount = rule.roundCount,
            gameCount = rule.gameCount,
        )
    }

}