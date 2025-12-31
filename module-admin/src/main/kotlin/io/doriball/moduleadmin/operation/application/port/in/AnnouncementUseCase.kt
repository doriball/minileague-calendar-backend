package io.doriball.moduleadmin.operation.application.port.`in`

import io.doriball.moduleadmin.operation.application.dto.AnnouncementDetailDto
import io.doriball.moduleadmin.operation.application.dto.AnnouncementDto
import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateAnnouncementCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadAnnouncementsCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateAnnouncementCommand

interface AnnouncementUseCase {

    fun getAnnouncements(command: ReadAnnouncementsCommand): Pair<List<AnnouncementDto>, Long>

    fun getAnnouncementDetail(announcementId: String): AnnouncementDetailDto

    fun createAnnouncement(command: CreateAnnouncementCommand)

    fun updateAnnouncement(announcementId: String, command: UpdateAnnouncementCommand)

    fun deleteAnnouncement(announcementId: String)

}