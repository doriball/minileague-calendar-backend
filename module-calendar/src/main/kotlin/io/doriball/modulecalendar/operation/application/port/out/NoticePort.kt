package io.doriball.modulecalendar.operation.application.port.out

import io.doriball.modulecore.domain.operation.Notice

interface NoticePort {

    fun getNotices(page: Int?, size: Int?): Pair<List<Notice>, Long>

    fun getNoticeDetail(noticeId: String): Notice?

}