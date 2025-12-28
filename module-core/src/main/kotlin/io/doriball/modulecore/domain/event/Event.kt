package io.doriball.modulecore.domain.event

import io.doriball.modulecore.domain.store.Store
import java.time.LocalDateTime

class Event(
    val id: String,
    val store: Store,
    val name: String,
    val scheduledAt: LocalDateTime,
    val official: Boolean,
    val stages: List<EventStage>,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {

    fun getStageTypes(): List<String> = stages.map { it.type.name }
    fun getRoundCount(): Int = stages.sumOf { it.roundCount }
    fun getGameCount(): Int? {
        return if (stages.size == 1) stages[0].gameCountPerRound else null
    }

}