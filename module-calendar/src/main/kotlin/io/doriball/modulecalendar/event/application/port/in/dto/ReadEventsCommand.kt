package io.doriball.modulecalendar.event.application.port.`in`.dto

data class ReadEventsCommand(
    val year: Int,
    val month: Int,
    val regionNo: Int?
)
