package io.doriball.moduleadmin.operation.adapter.out.persistence

import io.doriball.moduleadmin.operation.adapter.out.persistence.repository.AnnouncementMongoRepository
import io.doriball.moduleadmin.operation.application.port.out.AnnouncementPort
import io.doriball.moduleadmin.operation.common.enums.AnnouncementKeywordSearchType
import io.doriball.moduleadmin.operation.domain.AnnouncementCreate
import io.doriball.moduleadmin.operation.domain.AnnouncementUpdate
import io.doriball.modulecore.domain.operation.Announcement
import io.doriball.moduleinfrastructure.persistence.entity.AnnouncementDocument
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class AnnouncementQueryPersistenceAdapter(
    val mongoOperations: MongoOperations,
    val repository: AnnouncementMongoRepository
) : AnnouncementPort {

    override fun getAnnouncements(
        page: Int?,
        size: Int?,
        searchType: AnnouncementKeywordSearchType?,
        keyword: String?
    ): Pair<List<Announcement>, Long> {
        val pageable = PageRequest.of(page ?: 0, size ?: 10, Sort.by(Sort.Direction.DESC, "createdAt"))
        val query = Query().with(pageable)

        if (searchType != null && !keyword.isNullOrBlank()) {
            val field = when (searchType) {
                AnnouncementKeywordSearchType.TITLE -> "title"
                AnnouncementKeywordSearchType.CONTENT -> "content"
            }
            query.addCriteria(Criteria.where(field).regex(keyword, "i"))
        }

        val total = mongoOperations.count(query, AnnouncementDocument::class.java)
        val announcements = mongoOperations.find(query, AnnouncementDocument::class.java)
            .map { DocumentConvertUtil.convertToAnnouncement(it) }

        return Pair(announcements, total)
    }

    override fun getAnnouncementDetail(announcementId: String): Announcement? {
        val announcement = repository.findByIdOrNull(announcementId) ?: return null
        return DocumentConvertUtil.convertToAnnouncement(announcement)
    }

    override fun createAnnouncement(create: AnnouncementCreate) {
        val document = toAnnouncementDocument(create)
        repository.save(document)
    }

    override fun updateAnnouncement(update: AnnouncementUpdate) {
        val document = toAnnouncementDocument(update)
        repository.save(document)
    }

    override fun deleteAnnouncement(announcementId: String) {
        repository.deleteById(announcementId)
    }

    private fun toAnnouncementDocument(create: AnnouncementCreate): AnnouncementDocument = AnnouncementDocument(
        title = create.title,
        content = create.content,
    )

    private fun toAnnouncementDocument(update: AnnouncementUpdate): AnnouncementDocument = AnnouncementDocument(
        title = update.title,
        content = update.content,
    ).apply { id = update.id }

}