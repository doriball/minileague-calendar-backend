package io.doriball.modulecalendar.place.application.port.out

import io.doriball.modulecore.domain.place.Place

interface PlacePort {

    fun getStorePlaces(regionNo: Int?): List<Place>

    fun getStorePlaceDetail(storeId: String): Place?

}