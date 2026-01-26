package io.doriball.modulecalendar.operation.application.service

import io.doriball.modulecalendar.operation.application.dto.NoticeDetailDto
import io.doriball.modulecalendar.operation.application.dto.NoticeDto
import io.doriball.modulecalendar.operation.application.port.`in`.NoticeUseCase
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticeDetailCommand
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.modulecalendar.operation.application.port.out.NoticePort
import io.doriball.modulecore.shared.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class NoticeServiceV1(val noticePort: NoticePort) : NoticeUseCase {

    override fun getNotices(command: ReadNoticesCommand): Pair<List<NoticeDto>, Long> {
        val (notices, total) = noticePort.getNotices(command.page, command.size)
        return notices.map { NoticeDto.from(it) } to total
    }

    override fun getNoticeDetail(command: ReadNoticeDetailCommand): NoticeDetailDto {
        val notice = noticePort.getNoticeDetail(command.noticeId) ?: throw NotFoundException()
        return NoticeDetailDto.from(notice)
    }

}