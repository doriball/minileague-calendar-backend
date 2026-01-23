package io.doriball.moduleadmin.place.application.dto

import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.enums.PlaceType

class PlaceDetailDto(
    val id: String,
    val name: String,
    val region: String,
    val type: PlaceType,
    val address: String,
    val map: String?,
    val sns: String?,
) {

    companion object {
        fun from(place: Place): PlaceDetailDto = PlaceDetailDto(
            id = place.id,
            name = place.name,
            region = place.region.name,
            type = place.type,
            address = place.address,
            map = place.mapInformation,
            sns = place.sns,
        )
    }

    val displayType: String
        get() = when (type) {
            PlaceType.STORE -> "매장"
            PlaceType.ETC -> "기타"
        }
}