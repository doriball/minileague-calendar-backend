package io.doriball.moduleadmin.store.application.port.`in`.dto

import io.doriball.modulecore.enums.DayOfWeekType
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalTime

data class CreateStoreEventRuleCommand(
    @NotEmpty val name: String,
    @NotNull val dayOfWeek: DayOfWeekType,
    @NotNull val scheduledAt: LocalTime,
    @NotNull val official: Boolean,
    val entryFee: Long?,
    @NotEmpty val stages: List<@Valid CreateStoreEventRuleStageCommand>,
)
