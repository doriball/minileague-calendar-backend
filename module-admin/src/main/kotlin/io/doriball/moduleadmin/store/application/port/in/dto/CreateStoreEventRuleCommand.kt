package io.doriball.moduleadmin.store.application.port.`in`.dto

import io.doriball.modulecore.enums.DayOfWeekType
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateStoreEventRuleCommand(
    @NotEmpty val name: String,
    @NotEmpty val dayOfWeek: DayOfWeekType,
    @NotNull val scheduledAt: LocalDateTime,
    @NotNull val official: Boolean,
    @NotEmpty val stages: List<@Valid CreateStoreEventRuleStageCommand>,
)
