package io.doriball.modulecalendar.event.application.port.`in`

import io.doriball.modulecalendar.event.application.dto.EventDetailDto
import io.doriball.modulecalendar.event.application.dto.EventDto
import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventsCommand

interface EventUseCase {

    fun getEvents(command: ReadEventsCommand): List<EventDto>
    fun getEventDetail(command: ReadEventDetailCommand): EventDetailDto

}