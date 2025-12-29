package io.doriball.modulecalander.operation.application.service

import io.doriball.modulecalander.operation.application.dto.AnnouncementDetailDto
import io.doriball.modulecalander.operation.application.dto.AnnouncementDto
import io.doriball.modulecalander.operation.application.port.`in`.AnnouncementUseCase
import io.doriball.modulecalander.operation.application.port.`in`.dto.ReadAnnouncementDetailCommand
import io.doriball.modulecalander.operation.application.port.`in`.dto.ReadAnnouncementsCommand
import io.doriball.modulecalander.operation.application.port.out.AnnouncementPort
import io.doriball.modulecore.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class AnnouncementServiceV1(val announcementPort: AnnouncementPort) : AnnouncementUseCase {

    override fun getAnnouncements(command: ReadAnnouncementsCommand): Pair<List<AnnouncementDto>, Long> {
        val (announcements, total) = announcementPort.getAnnouncements(command.page, command.size)
        return announcements.map { AnnouncementDto.of(it) } to total
    }

    override fun getAnnouncementDetail(command: ReadAnnouncementDetailCommand): AnnouncementDetailDto {
        val announcement = announcementPort.getAnnouncementDetail(command.announcementId) ?: throw NotFoundException()
        return AnnouncementDetailDto.of(announcement)
    }

}