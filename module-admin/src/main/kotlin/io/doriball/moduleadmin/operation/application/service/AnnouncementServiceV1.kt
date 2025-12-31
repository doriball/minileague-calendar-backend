package io.doriball.moduleadmin.operation.application.service

import io.doriball.moduleadmin.operation.application.dto.AnnouncementDetailDto
import io.doriball.moduleadmin.operation.application.dto.AnnouncementDto
import io.doriball.moduleadmin.operation.application.port.`in`.AnnouncementUseCase
import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateAnnouncementCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadAnnouncementsCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateAnnouncementCommand
import io.doriball.moduleadmin.operation.application.port.out.AnnouncementPort
import io.doriball.moduleadmin.operation.domain.AnnouncementCreate
import io.doriball.moduleadmin.operation.domain.AnnouncementUpdate
import io.doriball.modulecore.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnnouncementServiceV1(val port: AnnouncementPort): AnnouncementUseCase {

    override fun getAnnouncements(command: ReadAnnouncementsCommand): Pair<List<AnnouncementDto>, Long> {
        val (announcements, total) = port.getAnnouncements(command.page, command.size, command.search, command.keyword)
        return announcements.map { AnnouncementDto.from(it) } to total
    }

    override fun getAnnouncementDetail(announcementId: String): AnnouncementDetailDto {
        val announcement = port.getAnnouncementDetail(announcementId) ?: throw NotFoundException()
        return AnnouncementDetailDto.from(announcement)
    }

    @Transactional
    override fun createAnnouncement(command: CreateAnnouncementCommand) {
        port.createAnnouncement(AnnouncementCreate.from(command))
    }

    @Transactional
    override fun updateAnnouncement(announcementId: String, command: UpdateAnnouncementCommand) {
        port.updateAnnouncement(AnnouncementUpdate.from(announcementId, command))
    }

    @Transactional
    override fun deleteAnnouncement(announcementId: String) {
        port.deleteAnnouncement(announcementId)
    }

}