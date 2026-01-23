package io.doriball.moduleadmin.place.domain

import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceEventRuleStageCommand
import io.doriball.modulecore.enums.StageType

class PlaceEventRuleStageUpdate(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(command: UpdatePlaceEventRuleStageCommand): PlaceEventRuleStageUpdate = PlaceEventRuleStageUpdate(
            stageNo = command.stageNo,
            type = command.type,
            roundCount = command.roundCount,
            gameCount = command.gameCount
        )
    }
}