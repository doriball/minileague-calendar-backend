package io.doriball.modulecore.domain.place

import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalDateTime
import java.time.LocalTime

class PlaceEventRule(
    val id: String,
    val placeId: String,
    val name: String,
    val dayOfWeek: DayOfWeekType,
    val scheduledAt: LocalTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val stages: List<PlaceEventRuleStage>,
    val entryFee: Long?,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {

    val stageTypes: List<String> get() = stages.map { it.type.name }
    val roundCount: Int get() = stages.sumOf { it.roundCount }
    val gameCount: Int?
        get() = if (stages.size == 1) stages[0].gameCountPerRound else null

}