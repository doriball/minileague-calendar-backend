package io.doriball.moduleadmin.event.application.service

import io.doriball.moduleadmin.event.application.dto.EventDetailDto
import io.doriball.moduleadmin.event.application.dto.EventDto
import io.doriball.moduleadmin.event.application.port.`in`.EventUseCase
import io.doriball.moduleadmin.event.application.port.`in`.dto.CreateEventCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.ReadEventsCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.UpdateEventCommand
import io.doriball.moduleadmin.event.application.port.out.EventPort
import io.doriball.moduleadmin.event.domain.EventCreate
import io.doriball.moduleadmin.event.domain.EventUpdate
import io.doriball.moduleadmin.event.common.exception.EventIsPassedException
import io.doriball.modulecore.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventServiceV1(val eventPort: EventPort) : EventUseCase {

    override fun getEvents(command: ReadEventsCommand): List<EventDto> {
        val events = eventPort.getEvents(
            year = command.year,
            month = command.month,
            regionNo = command.regionNo,
            official = command.official,
            searchType = command.search,
            keyword = command.keyword,
        )
        return events.map { EventDto.from(it) }
    }

    override fun getEventDetail(command: ReadEventDetailCommand): EventDetailDto {
        val event = eventPort.getEventDetail(eventId = command.eventId) ?: throw NotFoundException()
        return EventDetailDto.from(event)
    }

    @Transactional
    override fun createEvent(command: CreateEventCommand) {
        eventPort.createEvent(EventCreate.from(command))
    }

    @Transactional
    override fun updateEvent(eventId: String, command: UpdateEventCommand) {
        eventPort.updateEvent(EventUpdate.from(eventId, command))
    }

    @Transactional
    override fun deleteEvent(eventId: String) {
        if (eventPort.isPastEvent(eventId)) throw EventIsPassedException()
        eventPort.deleteEvent(eventId)
    }
}