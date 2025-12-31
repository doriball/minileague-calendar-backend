package io.doriball.moduleadmin.store.application.port.`in`.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateStoreCommand(
    @NotEmpty val name: String,
    @NotNull val regionNo: Int,
    @NotEmpty val address: String,
    val map: String?,
    val sns: String?,
)
