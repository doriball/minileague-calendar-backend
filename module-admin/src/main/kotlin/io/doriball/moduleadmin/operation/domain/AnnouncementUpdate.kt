package io.doriball.moduleadmin.operation.domain

import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateAnnouncementCommand

class AnnouncementUpdate(
    val id: String,
    val title: String,
    val content: String,
) {

    companion object {
        fun from(announcementId: String, command: UpdateAnnouncementCommand): AnnouncementUpdate = AnnouncementUpdate(
            id = announcementId,
            title = command.title,
            content = command.content,
        )
    }

}