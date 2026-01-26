package io.doriball.modulecalendar.event.application.service

import io.doriball.modulecalendar.event.application.dto.EventDetailDto
import io.doriball.modulecalendar.event.application.dto.EventDto
import io.doriball.modulecalendar.event.application.port.`in`.EventUseCase
import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventsCommand
import io.doriball.modulecalendar.event.application.port.out.EventPort
import io.doriball.modulecore.shared.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class EventServiceV1(val eventPort: EventPort) : EventUseCase {

    override fun getEvents(command: ReadEventsCommand): List<EventDto> {
        val events = eventPort.getEvents(command.year, command.month, command.regionNo)
        return events.map { EventDto.from(it) }
    }

    override fun getEventDetail(command: ReadEventDetailCommand): EventDetailDto {
        val event = eventPort.getEventDetail(command.eventId) ?: throw NotFoundException()
        return EventDetailDto.from(event)
    }

}