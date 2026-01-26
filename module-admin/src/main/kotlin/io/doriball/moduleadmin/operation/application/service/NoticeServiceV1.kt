package io.doriball.moduleadmin.operation.application.service

import io.doriball.moduleadmin.operation.application.dto.NoticeDetailDto
import io.doriball.moduleadmin.operation.application.dto.NoticeDto
import io.doriball.moduleadmin.operation.application.port.`in`.NoticeUseCase
import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateNoticeCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateNoticeCommand
import io.doriball.moduleadmin.operation.application.port.out.NoticePort
import io.doriball.moduleadmin.operation.domain.NoticeCreate
import io.doriball.moduleadmin.operation.domain.NoticeUpdate
import io.doriball.modulecore.shared.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeServiceV1(val port: NoticePort): NoticeUseCase {

    override fun getNotices(command: ReadNoticesCommand): Pair<List<NoticeDto>, Long> {
        val (notices, total) = port.getNotices(command.page, command.size, command.search, command.keyword)
        return notices.map { NoticeDto.from(it) } to total
    }

    override fun getNoticeDetail(noticeId: String): NoticeDetailDto {
        val notice = port.getNoticeDetail(noticeId) ?: throw NotFoundException()
        return NoticeDetailDto.from(notice)
    }

    @Transactional
    override fun createNotice(command: CreateNoticeCommand) {
        port.createNotice(NoticeCreate.from(command))
    }

    @Transactional
    override fun updateNotice(noticeId: String, command: UpdateNoticeCommand) {
        port.updateNotice(NoticeUpdate.from(noticeId, command))
    }

    @Transactional
    override fun deleteNotice(noticeId: String) {
        port.deleteNotice(noticeId)
    }

}