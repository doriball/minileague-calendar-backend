package io.doriball.moduleadmin.operation.application.port.out

import io.doriball.moduleadmin.operation.common.enums.AnnouncementKeywordSearchType
import io.doriball.moduleadmin.operation.domain.AnnouncementCreate
import io.doriball.moduleadmin.operation.domain.AnnouncementUpdate
import io.doriball.modulecore.domain.operation.Announcement

interface AnnouncementPort {

    fun getAnnouncements(
        page: Int?,
        size: Int?,
        searchType: AnnouncementKeywordSearchType?,
        keyword: String?
    ): Pair<List<Announcement>, Long>

    fun getAnnouncementDetail(announcementId: String): Announcement?

    fun createAnnouncement(create: AnnouncementCreate)

    fun updateAnnouncement(update: AnnouncementUpdate)

    fun deleteAnnouncement(announcementId: String)

}