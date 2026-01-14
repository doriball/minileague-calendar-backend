package io.doriball.modulecalendar.store.application.dto

import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.enums.LeagueCategoryType
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
        fun from(rule: StoreEventRule): StoreEventRuleDto = StoreEventRuleDto(
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