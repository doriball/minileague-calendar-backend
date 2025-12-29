package io.doriball.modulecalander.operation.adapter.`in`.web

import io.doriball.moduleapi.response.PaginationResultResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecalander.operation.application.dto.AnnouncementDetailDto
import io.doriball.modulecalander.operation.application.dto.AnnouncementDto
import io.doriball.modulecalander.operation.application.port.`in`.AnnouncementUseCase
import io.doriball.modulecalander.operation.application.port.`in`.dto.ReadAnnouncementDetailCommand
import io.doriball.modulecalander.operation.application.port.`in`.dto.ReadAnnouncementsCommand
import io.doriball.modulecore.util.PaginationUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AnnouncementController(val announcementUseCase: AnnouncementUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/announcements"
    }

    @GetMapping(BASE_PATH)
    fun getAnnouncements(command: ReadAnnouncementsCommand): PaginationResultResponse<AnnouncementDto> {
        val (contents, totalSize) = announcementUseCase.getAnnouncements(command)
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

    @GetMapping("$BASE_PATH/{announcementId}")
    fun getAnnouncementDetail(@PathVariable announcementId: String): SingleResultResponse<AnnouncementDetailDto> {
        val result = announcementUseCase.getAnnouncementDetail(ReadAnnouncementDetailCommand(announcementId))
        return APIResponseUtil.singeResultResponse(result)
    }

}