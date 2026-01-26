package io.doriball.modulecalendar.operation.application.service

import io.doriball.modulecalendar.operation.application.dto.NoticeDetailDto
import io.doriball.modulecalendar.operation.application.dto.NoticeDto
import io.doriball.modulecalendar.operation.application.dto.NoticePageDto
import io.doriball.modulecalendar.operation.application.port.`in`.NoticeUseCase
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticeDetailCommand
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.modulecalendar.operation.application.port.out.NoticePort
import io.doriball.modulecore.shared.codes.SharedCacheName
import io.doriball.modulecore.shared.exception.NotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class NoticeServiceV1(val noticePort: NoticePort) : NoticeUseCase {

    @Cacheable(value = [SharedCacheName.NOTICES], key = "(#command.page ?: '1') + '_' + (#command.size ?: '10')")
    override fun getNotices(command: ReadNoticesCommand): NoticePageDto {
        val (notices, total) = noticePort.getNotices(command.page, command.size)
        return NoticePageDto(notices = notices.map { NoticeDto.from(it) }, totalSize = total)
    }

    @Cacheable(value = [SharedCacheName.NOTICE_DETAIL], key = "#command.noticeId")
    override fun getNoticeDetail(command: ReadNoticeDetailCommand): NoticeDetailDto {
        val notice = noticePort.getNoticeDetail(command.noticeId) ?: throw NotFoundException()
        return NoticeDetailDto.from(notice)
    }

}