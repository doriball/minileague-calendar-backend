package io.doriball.modulecore.domain.operation

import java.time.LocalDateTime

class Announcement(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime? = null,
) {
}