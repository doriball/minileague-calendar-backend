package io.doriball.moduleadmin.place.application.port.out

import io.doriball.modulecore.domain.place.PlaceRegion

interface PlaceRegionPort {

    fun getPlaceRegions(): List<PlaceRegion>

}