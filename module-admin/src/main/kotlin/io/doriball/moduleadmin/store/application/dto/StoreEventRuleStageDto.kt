package io.doriball.moduleadmin.store.application.dto

import io.doriball.modulecore.domain.store.StoreEventRuleStage
import io.doriball.modulecore.enums.StageType

class StoreEventRuleStageDto(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(stage: StoreEventRuleStage): StoreEventRuleStageDto {
            return StoreEventRuleStageDto(stage.stageNo, stage.type, stage.roundCount, stage.gameCountPerRound)
        }
    }

}