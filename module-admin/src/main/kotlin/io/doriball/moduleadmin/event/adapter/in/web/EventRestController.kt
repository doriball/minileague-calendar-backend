package io.doriball.moduleadmin.event.adapter.`in`.web

import io.doriball.moduleadmin.event.application.dto.EventDetailDto
import io.doriball.moduleadmin.event.application.port.`in`.EventUseCase
import io.doriball.moduleadmin.event.application.port.`in`.dto.CreateEventCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.moduleadmin.event.application.port.`in`.dto.UpdateEventCommand
import io.doriball.moduleapi.response.CommonResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class EventRestController(private val useCase: EventUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/events"
    }

    @GetMapping("$BASE_PATH/{eventId}")
    fun getEventDetail(@PathVariable eventId: String): SingleResultResponse<EventDetailDto> {
        val result = useCase.getEventDetail(ReadEventDetailCommand(eventId))
        return APIResponseUtil.singeResultResponse(result)
    }

    @PostMapping(BASE_PATH)
    fun createEvent(@Valid @RequestBody command: CreateEventCommand): CommonResponse {
        useCase.createEvent(command)
        return APIResponseUtil.successResponse()
    }

    @PutMapping("$BASE_PATH/{eventId}")
    fun updateEvent(@PathVariable eventId: String, @Valid @RequestBody command: UpdateEventCommand): CommonResponse {
        useCase.updateEvent(eventId, command)
        return APIResponseUtil.successResponse()
    }

    @DeleteMapping("$BASE_PATH/{eventId}")
    fun deleteEvent(@PathVariable eventId: String): CommonResponse {
        useCase.deleteEvent(eventId)
        return APIResponseUtil.successResponse()
    }

}