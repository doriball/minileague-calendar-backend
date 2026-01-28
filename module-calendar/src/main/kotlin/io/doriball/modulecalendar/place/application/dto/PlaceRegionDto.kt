package io.doriball.modulecalendar.place.application.dto

import io.doriball.modulecore.domain.place.PlaceRegion

class PlaceRegionDto(val regionNo: Int, val name: String) {

    companion object {
        fun of(placeRegion: PlaceRegion) = PlaceRegionDto(
            regionNo = placeRegion.regionNo, name = placeRegion.name
        )
    }
    
}