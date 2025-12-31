package io.doriball.moduleadmin.event.application.port.`in`.dto

import io.doriball.modulecore.enums.StageType
import jakarta.validation.constraints.NotNull

data class CreateEventStageCommand(
    @NotNull val stageNo: Int,
    @NotNull val type: StageType,
    @NotNull val roundCount: Int,
    @NotNull val gameCount: Int,
)
