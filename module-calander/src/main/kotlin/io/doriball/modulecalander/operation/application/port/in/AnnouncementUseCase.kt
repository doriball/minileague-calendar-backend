package io.doriball.modulecalander.operation.application.port.`in`

import io.doriball.modulecalander.operation.application.dto.AnnouncementDetailDto
import io.doriball.modulecalander.operation.application.dto.AnnouncementDto
import io.doriball.modulecalander.operation.application.port.`in`.dto.ReadAnnouncementDetailCommand
import io.doriball.modulecalander.operation.application.port.`in`.dto.ReadAnnouncementsCommand

interface AnnouncementUseCase {

    fun getAnnouncements(command: ReadAnnouncementsCommand): Pair<List<AnnouncementDto>, Long>

    fun getAnnouncementDetail(command: ReadAnnouncementDetailCommand): AnnouncementDetailDto

}