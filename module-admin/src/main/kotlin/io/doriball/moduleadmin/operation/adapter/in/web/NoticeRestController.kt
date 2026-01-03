package io.doriball.moduleadmin.operation.adapter.`in`.web

import io.doriball.moduleadmin.operation.application.dto.NoticeDetailDto
import io.doriball.moduleadmin.operation.application.port.`in`.NoticeUseCase
import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateNoticeCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateNoticeCommand
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class NoticeRestController(val useCase: NoticeUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/notices"
    }

    @GetMapping("$BASE_PATH/{noticeId}")
    fun getAnnouncementDetail(@PathVariable noticeId: String): SingleResultResponse<NoticeDetailDto> {
        val result = useCase.getNoticeDetail(noticeId)
        return APIResponseUtil.singeResultResponse(result)
    }

    @PostMapping(BASE_PATH)
    fun createAnnouncement(@Valid @RequestBody command: CreateNoticeCommand) {
        useCase.createNotice(command)
    }

    @PutMapping("$BASE_PATH/{noticeId}")
    fun updateAnnouncement(
        @PathVariable noticeId: String,
        @Valid @RequestBody command: UpdateNoticeCommand
    ) {
        useCase.updateNotice(noticeId, command)
    }

    @DeleteMapping("$BASE_PATH/{noticeId}")
    fun deleteAnnouncement(@PathVariable noticeId: String) {
        useCase.deleteNotice(noticeId)
    }

}