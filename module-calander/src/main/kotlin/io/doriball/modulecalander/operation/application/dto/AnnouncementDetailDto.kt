package io.doriball.modulecalander.operation.application.dto

import io.doriball.modulecore.domain.operation.Announcement
import java.time.LocalDateTime

class AnnouncementDetailDto(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime? = null,
) {

    companion object {
        fun from(announcement: Announcement): AnnouncementDetailDto = AnnouncementDetailDto(
            id = announcement.id,
            title = announcement.title,
            content = announcement.content,
            createdAt = announcement.createdAt
        )
    }

}