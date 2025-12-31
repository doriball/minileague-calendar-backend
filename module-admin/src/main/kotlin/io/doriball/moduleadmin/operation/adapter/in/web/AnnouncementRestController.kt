package io.doriball.moduleadmin.operation.adapter.`in`.web

import io.doriball.moduleadmin.operation.application.dto.AnnouncementDetailDto
import io.doriball.moduleadmin.operation.application.port.`in`.AnnouncementUseCase
import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateAnnouncementCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateAnnouncementCommand
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class AnnouncementRestController(val useCase: AnnouncementUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/announcements"
    }

    @GetMapping("$BASE_PATH/{announcementId}")
    fun getAnnouncementDetail(@PathVariable announcementId: String): SingleResultResponse<AnnouncementDetailDto> {
        val result = useCase.getAnnouncementDetail(announcementId)
        return APIResponseUtil.singeResultResponse(result)
    }

    @PostMapping(BASE_PATH)
    fun createAnnouncement(@Valid @RequestBody command: CreateAnnouncementCommand) {
        useCase.createAnnouncement(command)
    }

    @PutMapping("$BASE_PATH/{announcementId}")
    fun updateAnnouncement(
        @PathVariable announcementId: String,
        @Valid @RequestBody command: UpdateAnnouncementCommand
    ) {
        useCase.updateAnnouncement(announcementId, command)
    }

    @DeleteMapping("$BASE_PATH/{announcementId}")
    fun deleteAnnouncement(@PathVariable announcementId: String) {
        useCase.deleteAnnouncement(announcementId)
    }

}