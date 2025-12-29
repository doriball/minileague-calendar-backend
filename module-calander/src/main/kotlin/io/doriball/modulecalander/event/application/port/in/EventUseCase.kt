package io.doriball.modulecalander.event.application.port.`in`

import io.doriball.modulecalander.event.application.dto.EventDetailDto
import io.doriball.modulecalander.event.application.dto.EventDto
import io.doriball.modulecalander.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.modulecalander.event.application.port.`in`.dto.ReadEventsCommand

interface EventUseCase {

    fun getEvents(command: ReadEventsCommand): List<EventDto>
    fun getEventDetail(command: ReadEventDetailCommand): EventDetailDto

}