package io.doriball.moduleadmin.event.application.port.`in`.dto

import io.doriball.moduleadmin.event.common.enums.EventKeywordSearchType

data class ReadEventsCommand(
    val year: Int,
    val month: Int,
    val regionNo: Int?,
    val official: Boolean?,
    val search: EventKeywordSearchType?,
    val keyword: String?,
)
