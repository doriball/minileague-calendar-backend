package io.doriball.moduleadmin.operation.domain

import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateNoticeCommand

class NoticeUpdate(
    val id: String,
    val title: String,
    val content: String,
) {

    companion object {
        fun from(noticeId: String, command: UpdateNoticeCommand): NoticeUpdate = NoticeUpdate(
            id = noticeId,
            title = command.title,
            content = command.content,
        )
    }

}