package io.doriball.moduleadmin.place.application.port.`in`

import io.doriball.moduleadmin.place.application.dto.PlaceRegionDto

interface PlaceRegionUseCase {

    fun getPlaceRegions(): List<PlaceRegionDto>

}