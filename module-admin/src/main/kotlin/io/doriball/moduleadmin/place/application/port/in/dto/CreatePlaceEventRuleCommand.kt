package io.doriball.moduleadmin.place.application.port.`in`.dto

import io.doriball.modulecore.domain.enums.DayOfWeekType
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalTime

data class CreatePlaceEventRuleCommand(
    @NotEmpty val name: String,
    @NotNull val dayOfWeek: DayOfWeekType,
    @NotNull val scheduledAt: LocalTime,
    @NotNull val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    @NotEmpty val stages: List<@Valid CreatePlaceEventRuleStageCommand>,
)
