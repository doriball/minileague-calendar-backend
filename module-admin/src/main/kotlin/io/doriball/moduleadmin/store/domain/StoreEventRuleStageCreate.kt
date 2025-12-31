package io.doriball.moduleadmin.store.domain

import io.doriball.moduleadmin.store.application.port.`in`.dto.CreateStoreEventRuleStageCommand
import io.doriball.modulecore.enums.StageType

class StoreEventRuleStageCreate(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(command: CreateStoreEventRuleStageCommand): StoreEventRuleStageCreate = StoreEventRuleStageCreate(
            stageNo = command.stageNo,
            type = command.type,
            roundCount = command.roundCount,
            gameCount = command.gameCount
        )
    }
}