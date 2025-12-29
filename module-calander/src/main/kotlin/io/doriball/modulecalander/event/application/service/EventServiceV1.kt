package io.doriball.modulecalander.event.application.service

import io.doriball.modulecalander.event.application.dto.EventDetailDto
import io.doriball.modulecalander.event.application.dto.EventDto
import io.doriball.modulecalander.event.application.port.`in`.EventUseCase
import io.doriball.modulecalander.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.modulecalander.event.application.port.`in`.dto.ReadEventsCommand
import io.doriball.modulecalander.event.application.port.out.EventPort
import io.doriball.modulecore.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class EventServiceV1(val eventPort: EventPort) : EventUseCase {

    override fun getEvents(command: ReadEventsCommand): List<EventDto> {
        val events = eventPort.getEvents(command.year, command.month, command.regionNo)
        return events.map { EventDto.of(it) }
    }

    override fun getEventDetail(command: ReadEventDetailCommand): EventDetailDto {
        val event = eventPort.getEventDetail(command.eventId) ?: throw NotFoundException()
        return EventDetailDto.of(event)
    }

}