package io.doriball.modulecore.domain.store

import io.doriball.modulecore.enums.StageType

class StoreEventRuleStage(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCountPerRound: Int,
) {
}