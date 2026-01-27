package io.doriball.moduleadmin.operation.adapter.out.persistence

import io.doriball.moduleadmin.operation.adapter.out.persistence.repository.NoticeMongoRepository
import io.doriball.moduleadmin.operation.application.port.out.NoticePort
import io.doriball.moduleadmin.operation.common.enums.NoticeKeywordSearchType
import io.doriball.moduleadmin.operation.domain.NoticeCreate
import io.doriball.moduleadmin.operation.domain.NoticeUpdate
import io.doriball.modulecore.domain.operation.Notice
import io.doriball.modulecore.shared.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.NoticeDocument
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class NoticeQueryPersistenceAdapter(
    private val mongoOperations: MongoOperations,
    private val repository: NoticeMongoRepository
) : NoticePort {

    override fun getNotices(
        page: Int?,
        size: Int?,
        searchType: NoticeKeywordSearchType?,
        keyword: String?
    ): Pair<List<Notice>, Long> {
        val convertedPage = if (page == null || page <= 0) 0 else page - 1
        val pageable = PageRequest.of(convertedPage, size ?: 10, Sort.by(Sort.Direction.DESC, "createdAt"))
        val query = Query().with(pageable)

        if (searchType != null && !keyword.isNullOrBlank()) {
            val field = when (searchType) {
                NoticeKeywordSearchType.TITLE -> "title"
                NoticeKeywordSearchType.CONTENT -> "content"
            }
            query.addCriteria(Criteria.where(field).regex(keyword, "i"))
        }

        val countQuery = Query.of(query).limit(0).skip(0)
        val total = mongoOperations.count(countQuery, NoticeDocument::class.java)
        val announcements = mongoOperations.find(query, NoticeDocument::class.java)
            .map { DocumentConvertUtil.convertToNotice(it) }

        return Pair(announcements, total)
    }

    override fun getNoticeDetail(noticeId: String): Notice? {
        val announcement = repository.findByIdOrNull(noticeId) ?: return null
        return DocumentConvertUtil.convertToNotice(announcement)
    }

    override fun createNotice(create: NoticeCreate) {
        val document = toNoticeDocument(create)
        repository.save(document)
    }

    override fun updateNotice(update: NoticeUpdate) {
        val notice = repository.findByIdOrNull(update.id) ?: throw NotFoundException()
        notice.apply {
            title = update.title
            content = update.content
        }
        repository.save(notice)
    }

    override fun deleteNotice(noticeId: String) {
        repository.deleteById(noticeId)
    }

    private fun toNoticeDocument(create: NoticeCreate): NoticeDocument = NoticeDocument(
        title = create.title,
        content = create.content,
    )

}