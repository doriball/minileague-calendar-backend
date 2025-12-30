package io.doriball.modulecalander.operation.adapter.out.persistence

import io.doriball.modulecalander.operation.adapter.out.persistence.repository.AnnouncementMongoRepository
import io.doriball.modulecalander.operation.application.port.out.AnnouncementPort
import io.doriball.modulecore.domain.operation.Announcement
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AnnouncementQueryPersistenceAdapter(val repository: AnnouncementMongoRepository) : AnnouncementPort {

    override fun getAnnouncements(page: Int?, size: Int?): Pair<List<Announcement>, Long> {
        val convertedPage = page?.minus(1) ?: 0
        val pageable = PageRequest.of(
            convertedPage,
            size ?: 10,
            Sort.by("createdAt").descending()
        )
        val announcements = repository.findAll(pageable)
        return Pair(
            announcements.map { DocumentConvertUtil.convertToAnnouncement(it) }.toList(),
            announcements.totalElements
        )
    }

    override fun getAnnouncementDetail(announcementId: String): Announcement? {
        val announcementDocument = repository.findByIdOrNull(announcementId) ?: return null
        return DocumentConvertUtil.convertToAnnouncement(announcementDocument)
    }

}