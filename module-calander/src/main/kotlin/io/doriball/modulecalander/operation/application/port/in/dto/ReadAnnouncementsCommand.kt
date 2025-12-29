package io.doriball.modulecalander.operation.application.port.`in`.dto

data class ReadAnnouncementsCommand(val page: Int? = 1, val size: Int? = 10)
