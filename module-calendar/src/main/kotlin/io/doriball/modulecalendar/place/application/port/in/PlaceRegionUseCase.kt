package io.doriball.modulecalendar.place.application.port.`in`

import io.doriball.modulecalendar.place.application.dto.PlaceRegionDto

interface PlaceRegionUseCase {

    fun getPlaceRegions(): List<PlaceRegionDto>

}