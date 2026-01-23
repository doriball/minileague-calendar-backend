package io.doriball.modulecore.domain.event

import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.enums.LeagueCategoryType
import java.time.LocalDateTime

class Event(
    val id: String,
    val place: Place,
    val name: String,
    val scheduledAt: LocalDateTime,
    val category: LeagueCategoryType,
    val capacity: Int?,
    val stages: List<EventStage>,
    val entryFee: Long? = 0L,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {

    val storeId: String get() = place.id
    val storeName: String get() = place.name
    val regionName: String get() = place.regionName
    val stageTypes: List<String> get() = stages.map { it.type.name }
    val roundCount: Int get() = stages.sumOf { it.roundCount }
    val gameCount: Int?
        get() = if (stages.size == 1) stages[0].gameCountPerRound else null

}