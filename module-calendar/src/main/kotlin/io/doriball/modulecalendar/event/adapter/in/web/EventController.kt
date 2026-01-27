package io.doriball.modulecalendar.event.adapter.`in`.web

import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecalendar.event.application.dto.EventDetailDto
import io.doriball.modulecalendar.event.application.dto.EventDto
import io.doriball.modulecalendar.event.application.port.`in`.EventUseCase
import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventsCommand
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class EventController(private val eventUseCase: EventUseCase) {

    companion object {
        const val BASE_PATH = "/v1/events"
    }

    @GetMapping(BASE_PATH)
    fun getEvents(command: ReadEventsCommand): ListResultResponse<EventDto> {
        val result = eventUseCase.getEvents(command)
        return APIResponseUtil.listResultResponse(result)
    }

    @GetMapping("$BASE_PATH/{eventId}")
    fun getEventDetail(@PathVariable eventId: String): SingleResultResponse<EventDetailDto> {
        val result = eventUseCase.getEventDetail(ReadEventDetailCommand(eventId))
        return APIResponseUtil.singeResultResponse(result)
    }

}