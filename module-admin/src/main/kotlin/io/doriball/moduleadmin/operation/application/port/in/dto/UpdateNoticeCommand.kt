package io.doriball.moduleadmin.operation.application.port.`in`.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class UpdateNoticeCommand(
    @NotEmpty @Size(max = 30) val title: String,
    @NotEmpty @Size(max = 5000) val content: String,
)
