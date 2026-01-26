package io.doriball.moduleadmin.place.application.dto

import io.doriball.modulecore.domain.place.PlaceEventRuleStage
import io.doriball.modulecore.domain.enums.StageType

class PlaceEventRuleStageDto(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(stage: PlaceEventRuleStage): PlaceEventRuleStageDto {
            return PlaceEventRuleStageDto(stage.stageNo, stage.type, stage.roundCount, stage.gameCountPerRound)
        }
    }

}