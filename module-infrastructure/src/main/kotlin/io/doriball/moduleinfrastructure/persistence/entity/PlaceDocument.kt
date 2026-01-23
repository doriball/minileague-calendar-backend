package io.doriball.moduleinfrastructure.persistence.entity

import io.doriball.modulecore.enums.PlaceType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "place")
class PlaceDocument(
    val name: String,
    val regionNo: Int,
    val type: PlaceType,
    val address: String,
    val mapInformation: String?,
    val sns: String?,
) : BaseTimeDocument() {

    @Id var id: String? = null

}