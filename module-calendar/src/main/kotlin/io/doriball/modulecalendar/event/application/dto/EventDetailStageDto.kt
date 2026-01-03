package io.doriball.modulecalendar.event.application.dto

import io.doriball.modulecore.domain.event.EventStage
import io.doriball.modulecore.enums.StageType

class EventDetailStageDto(
    val stageNo: Int,
    val type: StageType,
    val roundCount: Int,
    val gameCount: Int,
) {

    companion object {
        fun from(stage: EventStage): EventDetailStageDto {
            return EventDetailStageDto(stage.stageNo, stage.type, stage.roundCount, stage.gameCountPerRound)
        }
    }

}
