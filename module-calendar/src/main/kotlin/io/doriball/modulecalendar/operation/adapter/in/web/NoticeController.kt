package io.doriball.modulecalendar.operation.adapter.`in`.web

import io.doriball.moduleapi.response.PaginationResultResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecalendar.operation.application.dto.NoticeDetailDto
import io.doriball.modulecalendar.operation.application.dto.NoticeDto
import io.doriball.modulecalendar.operation.application.port.`in`.NoticeUseCase
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticeDetailCommand
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.modulecore.util.PaginationUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class NoticeController(val noticeUseCase: NoticeUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/notices"
    }

    @GetMapping(BASE_PATH)
    fun getNotices(command: ReadNoticesCommand): PaginationResultResponse<NoticeDto> {
        val (contents, totalSize) = noticeUseCase.getNotices(command)
        val page = command.page ?: 1
        val size = command.size ?: 10

        return APIResponseUtil.paginationResultResponse(
            contents = contents,
            page = page,
            pageSize = size,
            totalSize = totalSize,
            totalPages = PaginationUtil.calculateTotalPages(size, totalSize)
        )
    }

    @GetMapping("$BASE_PATH/{noticeId}")
    fun getNoticeDetail(@PathVariable noticeId: String): SingleResultResponse<NoticeDetailDto> {
        val result = noticeUseCase.getNoticeDetail(ReadNoticeDetailCommand(noticeId))
        return APIResponseUtil.singeResultResponse(result)
    }

}