package io.doriball.modulecalander.operation.application.dto

import io.doriball.modulecore.domain.operation.Announcement
import java.time.LocalDateTime

class AnnouncementDto(
    val id: String,
    val title: String,
    val createdAt: LocalDateTime
) {

    companion object {
        fun of(announcement: Announcement): AnnouncementDto =
            AnnouncementDto(id = announcement.id, title = announcement.title, createdAt = announcement.createdAt)
    }

}