package io.doriball.modulecore.domain.store

import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalDateTime
import java.time.LocalTime

class StoreEventRule(
    val id: String,
    val storeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val stages: List<StoreEventRuleStage>,
    val entryFee: Long?,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {

    val stageTypes: List<String> get() = stages.map { it.type.name }
    val roundCount: Int get() = stages.sumOf { it.roundCount }
    val gameCount: Int?
        get() = if (stages.size == 1) stages[0].gameCountPerRound else null

}