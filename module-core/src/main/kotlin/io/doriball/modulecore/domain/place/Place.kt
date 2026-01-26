package io.doriball.modulecore.domain.place

import io.doriball.modulecore.domain.enums.PlaceType
import java.time.LocalDateTime

class Place(
    val id: String,
    val name: String,
    val region: PlaceRegion,
    val type: PlaceType,
    val address: String,
    val mapInformation: String?,
    val sns: String?,
    val eventRules: List<PlaceEventRule>? = listOf(),
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {

    val regionName: String get() = region.name

}