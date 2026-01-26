package io.doriball.moduleadmin.place.application.port.`in`.dto

import io.doriball.modulecore.domain.enums.PlaceType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreatePlaceCommand(
    @NotEmpty val name: String,
    @NotNull val regionNo: Int,
    @NotNull val type: PlaceType,
    @NotEmpty val address: String,
    val map: String?,
    val sns: String?,
)
