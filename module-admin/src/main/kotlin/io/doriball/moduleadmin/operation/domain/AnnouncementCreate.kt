package io.doriball.moduleadmin.operation.domain

import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateAnnouncementCommand

class AnnouncementCreate(
    val title: String,
    val content: String,
) {

    companion object {
        fun from(command: CreateAnnouncementCommand): AnnouncementCreate = AnnouncementCreate(
            title = command.title,
            content = command.content,
        )
    }

}