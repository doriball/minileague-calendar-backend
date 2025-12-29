package io.doriball.modulecalander.operation.application.port.out

import io.doriball.modulecore.domain.operation.Announcement

interface AnnouncementPort {

    fun getAnnouncements(page: Int?, size: Int?): Pair<List<Announcement>, Long>

    fun getAnnouncementDetail(announcementId: String): Announcement?

}