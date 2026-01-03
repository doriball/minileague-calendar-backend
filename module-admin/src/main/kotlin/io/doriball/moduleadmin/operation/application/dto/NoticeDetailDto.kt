package io.doriball.moduleadmin.operation.application.dto

import io.doriball.modulecore.domain.operation.Notice
import java.time.LocalDateTime

class NoticeDetailDto(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime? = null,
) {

    companion object {
        fun from(notice: Notice): NoticeDetailDto = NoticeDetailDto(
            id = notice.id,
            title = notice.title,
            content = notice.content,
            createdAt = notice.createdAt,
        )
    }

}