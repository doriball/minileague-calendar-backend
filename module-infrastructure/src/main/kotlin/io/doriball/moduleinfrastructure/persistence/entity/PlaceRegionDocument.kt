package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "place_region")
class PlaceRegionDocument(
    var regionNo: Int,
    var name: String,
) : BaseTimeDocument() {

    @Id var id: String? = null

}