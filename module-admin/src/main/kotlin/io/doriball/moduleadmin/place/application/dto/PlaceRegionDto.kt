package io.doriball.moduleadmin.place.application.dto

import io.doriball.modulecore.domain.place.PlaceRegion

class PlaceRegionDto(
    val regionNo: Int,
    val name: String,
) {

    companion object {
        fun from(placeRegion: PlaceRegion) = PlaceRegionDto(
            regionNo = placeRegion.regionNo, name = placeRegion.name
        )
    }

}