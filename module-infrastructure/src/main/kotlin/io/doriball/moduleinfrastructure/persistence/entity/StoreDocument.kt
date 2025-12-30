package io.doriball.moduleinfrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "store")
class StoreDocument(
    val name: String,
    val regionNo: Int,
    val address: String,
    val mapInformation: String?,
    val sns: String?,
) : BaseTimeDocument() {

    @Id var id: String? = null

}