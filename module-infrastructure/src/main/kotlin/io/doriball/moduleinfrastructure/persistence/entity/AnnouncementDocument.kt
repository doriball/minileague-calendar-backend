package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "announcement")
class AnnouncementDocument(
    @Id val id: String,
    val title: String,
    val content: String,
): BaseTimeDocument() {
}