package io.doriball.moduleadmin.place.domain

import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceEventRuleStageCommand
import io.doriball.modulecore.domain.enums.StageType

class PlaceEventRuleStageCreate(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(command: CreatePlaceEventRuleStageCommand): PlaceEventRuleStageCreate = PlaceEventRuleStageCreate(
            stageNo = command.stageNo,
            type = command.type,
            roundCount = command.roundCount,
            gameCount = command.gameCount
        )
    }
}