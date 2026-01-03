package io.doriball.moduleadmin.operation.application.port.`in`.dto

import io.doriball.moduleadmin.operation.common.enums.NoticeKeywordSearchType

data class ReadNoticesCommand(
    val page: Int?,
    val size: Int?,
    val search: NoticeKeywordSearchType?,
    val keyword: String?,
)
