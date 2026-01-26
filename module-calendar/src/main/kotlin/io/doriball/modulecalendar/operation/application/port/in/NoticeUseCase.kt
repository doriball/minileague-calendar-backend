package io.doriball.modulecalendar.operation.application.port.`in`

import io.doriball.modulecalendar.operation.application.dto.NoticeDetailDto
import io.doriball.modulecalendar.operation.application.dto.NoticeDto
import io.doriball.modulecalendar.operation.application.dto.NoticePageDto
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticeDetailCommand
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticesCommand

interface NoticeUseCase {

    fun getNotices(command: ReadNoticesCommand): NoticePageDto

    fun getNoticeDetail(command: ReadNoticeDetailCommand): NoticeDetailDto

}