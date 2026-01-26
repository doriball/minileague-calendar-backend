package io.doriball.moduleadmin.event.domain

import io.doriball.moduleadmin.event.application.port.`in`.dto.UpdateEventStageCommand
import io.doriball.modulecore.domain.enums.StageType

class EventStageUpdate(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(command: UpdateEventStageCommand): EventStageUpdate =
            EventStageUpdate(
                stageNo = command.stageNo,
                type = command.type,
                roundCount = command.roundCount,
                gameCount = command.gameCount
            )
    }

}