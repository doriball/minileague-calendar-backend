package io.doriball.moduleadmin.place.application.port.`in`.dto

import io.doriball.modulecore.domain.enums.StageType
import jakarta.validation.constraints.NotNull

data class UpdatePlaceEventRuleStageCommand(
    @NotNull val stageNo: Int,
    @NotNull val type: StageType,
    @NotNull val roundCount: Int,
    @NotNull val gameCount: Int,
)
