package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.enums.StageType

class EventStageDocument(
    val stageNo: Int,
    val type: String,
    val roundCount: Int,
    val gameCountPerRound: Int,
) {

    fun getType(): StageType = StageType.valueOf(type)

}