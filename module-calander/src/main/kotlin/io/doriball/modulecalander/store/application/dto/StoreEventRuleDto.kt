package io.doriball.modulecalander.store.application.dto

import io.doriball.modulecore.domain.store.StoreEventRule
import java.time.LocalDateTime

class StoreEventRuleDto(
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val types: List<String>,
    val roundCount: Int,
    val gameCount: Int?,
) {

    companion object {
        fun of(rule: StoreEventRule): StoreEventRuleDto = StoreEventRuleDto(
            scheduledAt = rule.scheduledAt,
            official = rule.official,
            types = rule.stageTypes,
            roundCount = rule.roundCount,
            gameCount = rule.gameCount,
        )
    }

}