package io.doriball.moduleadmin.store.domain

import io.doriball.moduleadmin.store.application.port.`in`.dto.UpdateStoreEventRuleStageCommand
import io.doriball.modulecore.enums.StageType

class StoreEventRuleStageUpdate(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(command: UpdateStoreEventRuleStageCommand): StoreEventRuleStageUpdate = StoreEventRuleStageUpdate(
            stageNo = command.stageNo,
            type = command.type,
            roundCount = command.roundCount,
            gameCount = command.gameCount
        )
    }
}