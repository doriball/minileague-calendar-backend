package io.doriball.modulecore.domain.store

import io.doriball.modulecore.enums.DayOfWeekType
import java.time.LocalDateTime

class StoreEventRule(
    val id: String,
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: List<StoreEventRuleStage>,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
) {

    val stageTypes: List<String> get() = stages.map { it.type.name }
    val roundCount: Int get() = stages.sumOf { it.roundCount }
    val gameCount: Int?
        get() = if (stages.size == 1) stages[0].gameCountPerRound else null

}