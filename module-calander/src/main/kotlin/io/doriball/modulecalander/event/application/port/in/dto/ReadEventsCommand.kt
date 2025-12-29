package io.doriball.modulecalander.event.application.port.`in`.dto

data class ReadEventsCommand(
    val year: Int,
    val month: Int,
    val regionNo: Int?
)
