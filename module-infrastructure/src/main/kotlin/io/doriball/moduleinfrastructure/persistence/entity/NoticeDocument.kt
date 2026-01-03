package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "notice")
class NoticeDocument(
    val title: String,
    val content: String,
) : BaseTimeDocument() {

    @Id var id: String? = null

}