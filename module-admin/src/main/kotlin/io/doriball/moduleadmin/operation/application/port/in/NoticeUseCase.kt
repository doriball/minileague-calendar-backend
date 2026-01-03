package io.doriball.moduleadmin.operation.application.port.`in`

import io.doriball.moduleadmin.operation.application.dto.NoticeDetailDto
import io.doriball.moduleadmin.operation.application.dto.NoticeDto
import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateNoticeCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateNoticeCommand

interface NoticeUseCase {

    fun getNotices(command: ReadNoticesCommand): Pair<List<NoticeDto>, Long>

    fun getNoticeDetail(noticeId: String): NoticeDetailDto

    fun createNotice(command: CreateNoticeCommand)

    fun updateNotice(noticeId: String, command: UpdateNoticeCommand)

    fun deleteNotice(noticeId: String)

}