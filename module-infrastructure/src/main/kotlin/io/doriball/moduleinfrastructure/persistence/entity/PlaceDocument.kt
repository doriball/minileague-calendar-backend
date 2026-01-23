package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.enums.PlaceType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "place")
class PlaceDocument(
    var name: String,
    var regionNo: Int,
    var type: PlaceType,
    var address: String,
    var mapInformation: String?,
    var sns: String?,
) : BaseTimeDocument() {

    @Id var id: String? = null

}