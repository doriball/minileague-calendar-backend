package io.doriball.moduleadmin.place.application.port.`in`.dto

import io.doriball.modulecore.enums.StageType
import jakarta.validation.constraints.NotNull

data class CreatePlaceEventRuleStageCommand(
    @NotNull val stageNo: Int,
    @NotNull val type: StageType,
    @NotNull val roundCount: Int,
    @NotNull val gameCount: Int,
)
