package io.doriball.modulecalendar.operation.adapter.out.persistence

import io.doriball.modulecalendar.operation.adapter.out.persistence.repository.NoticeMongoRepository
import io.doriball.modulecalendar.operation.application.port.out.NoticePort
import io.doriball.modulecore.domain.operation.Notice
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class NoticeQueryPersistenceAdapter(private val repository: NoticeMongoRepository) : NoticePort {

    override fun getNotices(page: Int?, size: Int?): Pair<List<Notice>, Long> {
        val convertedPage = page?.minus(1) ?: 0
        val pageable = PageRequest.of(
            convertedPage,
            size ?: 10,
            Sort.by("createdAt").descending()
        )
        val announcements = repository.findAll(pageable)
        return Pair(
            announcements.map { DocumentConvertUtil.convertToNotice(it) }.toList(),
            announcements.totalElements
        )
    }

    override fun getNoticeDetail(noticeId: String): Notice? {
        val announcementDocument = repository.findByIdOrNull(noticeId) ?: return null
        return DocumentConvertUtil.convertToNotice(announcementDocument)
    }

}