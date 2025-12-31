package io.doriball.moduleadmin.operation.application.port.`in`.dto

import io.doriball.moduleadmin.operation.common.enums.AnnouncementKeywordSearchType

data class ReadAnnouncementsCommand(
    val page: Int?,
    val size: Int?,
    val search: AnnouncementKeywordSearchType?,
    val keyword: String?,
)
