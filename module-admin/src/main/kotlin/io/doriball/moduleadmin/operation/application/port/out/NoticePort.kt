package io.doriball.moduleadmin.operation.application.port.out

import io.doriball.moduleadmin.operation.common.enums.NoticeKeywordSearchType
import io.doriball.moduleadmin.operation.domain.NoticeCreate
import io.doriball.moduleadmin.operation.domain.NoticeUpdate
import io.doriball.modulecore.domain.operation.Notice

interface NoticePort {

    fun getNotices(
        page: Int?,
        size: Int?,
        searchType: NoticeKeywordSearchType?,
        keyword: String?
    ): Pair<List<Notice>, Long>

    fun getNoticeDetail(noticeId: String): Notice?

    fun createNotice(create: NoticeCreate)

    fun updateNotice(update: NoticeUpdate)

    fun deleteNotice(noticeId: String)

}