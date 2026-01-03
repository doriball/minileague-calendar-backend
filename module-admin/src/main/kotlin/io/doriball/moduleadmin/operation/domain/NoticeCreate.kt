package io.doriball.moduleadmin.operation.domain

import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateNoticeCommand

class NoticeCreate(
    val title: String,
    val content: String,
) {

    companion object {
        fun from(command: CreateNoticeCommand): NoticeCreate = NoticeCreate(
            title = command.title,
            content = command.content,
        )
    }

}