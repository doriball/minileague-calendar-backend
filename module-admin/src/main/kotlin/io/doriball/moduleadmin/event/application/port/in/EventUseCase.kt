package io.doriball.moduleadmin.event.application.port.`in`

import io.doriball.moduleadmin.event.application.dto.EventDetailDto
import io.doriball.moduleadmin.event.application.dto.EventDto
import io.doriball.moduleadmin.event.application.port.`in`.dto.CreateEventCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.ReadEventsCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.UpdateEventCommand

interface EventUseCase {

    fun getEvents(command: ReadEventsCommand): List<EventDto>

    fun getEventDetail(command: ReadEventDetailCommand): EventDetailDto

    fun createEvent(command: CreateEventCommand)

    fun updateEvent(eventId: String, command: UpdateEventCommand)

    fun deleteEvent(eventId: String)

}