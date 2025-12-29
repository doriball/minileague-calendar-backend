package io.doriball.modulecore.domain.match

import java.time.LocalDateTime

class MatchRecord(
    val id: String,
    val storeId: String,
    val eventId: String,
    val playerId: String,
    val deckIconIds: MutableList<Int>,
    val stages: MutableList<MatchRecordStage>,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {
}