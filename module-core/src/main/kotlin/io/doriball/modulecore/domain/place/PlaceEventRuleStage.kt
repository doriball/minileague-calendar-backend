package io.doriball.modulecore.domain.place

import io.doriball.modulecore.domain.enums.StageType

class PlaceEventRuleStage(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCountPerRound: Int,
) {
}