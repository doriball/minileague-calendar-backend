package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "store_region")
class StoreRegionDocument(
    @Id val id: String,
    val regionNo: Int,
    val name: String,
) : BaseTimeDocument() {
}