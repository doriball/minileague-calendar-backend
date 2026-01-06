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
    val entryFee: Long?,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {

    val storeId: String get() = store.id
    val storeName: String get() = store.name
    val regionName: String get() = store.regionName
    val stageTypes: List<String> get() = stages.map { it.type.name }
    val roundCount: Int get() = stages.sumOf { it.roundCount }
    val gameCount: Int?
        get() = if (stages.size == 1) stages[0].gameCountPerRound else null

}