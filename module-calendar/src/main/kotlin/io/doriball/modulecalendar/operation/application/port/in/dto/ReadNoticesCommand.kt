package io.doriball.modulecalendar.operation.application.port.`in`.dto

data class ReadNoticesCommand(val page: Int? = 1, val size: Int? = 10)
