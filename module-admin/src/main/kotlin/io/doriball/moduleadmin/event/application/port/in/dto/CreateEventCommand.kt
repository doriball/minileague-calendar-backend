package io.doriball.moduleadmin.event.application.port.`in`.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CreateEventCommand(
    @NotEmpty val name: String,
    @NotEmpty val storeId: String,
    @NotNull val scheduledAt: LocalDateTime,
    @NotNull val official: Boolean,
    val entryFee: Long?,
    @NotEmpty val stages: List<@Valid CreateEventStageCommand>,
)