package io.doriball.modulecore.domain.event

import io.doriball.modulecore.domain.enums.StageType

class EventStage(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCountPerRound: Int,
) {
}