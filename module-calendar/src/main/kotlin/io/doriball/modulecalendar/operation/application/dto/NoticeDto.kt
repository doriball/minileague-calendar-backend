package io.doriball.modulecalendar.operation.application.dto

import io.doriball.modulecore.domain.operation.Notice
import java.time.LocalDateTime

class NoticeDto(
    val id: String,
    val title: String,
    val createdAt: LocalDateTime? = null,
) {

    companion object {
        fun from(notice: Notice): NoticeDto =
            NoticeDto(id = notice.id, title = notice.title, createdAt = notice.createdAt)
    }

}