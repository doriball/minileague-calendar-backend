package io.doriball.moduleadmin.event.application.port.`in`.dto

import io.doriball.modulecore.domain.enums.LeagueCategoryType
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class UpdateEventCommand(
    @NotEmpty val name: String,
    @NotEmpty val placeId: String,
    @NotNull val scheduledAt: LocalDateTime,
    @NotNull val category: LeagueCategoryType,
    val capacity: Int?,
    val entryFee: Long?,
    @NotEmpty val stages: List<@Valid UpdateEventStageCommand>,
)