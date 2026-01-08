package io.doriball.moduleadmin.event.domain

import io.doriball.moduleadmin.event.application.port.`in`.dto.CreateEventStageCommand
import io.doriball.modulecore.enums.StageType

class EventStageCreate(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(command: CreateEventStageCommand): EventStageCreate =
            EventStageCreate(
                stageNo = command.stageNo,
                type = command.type,
                roundCount = command.roundCount,
                gameCount = command.gameCount
            )
    }

}