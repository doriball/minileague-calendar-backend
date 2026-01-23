package io.doriball.modulecalendar.place.application.port.out

import io.doriball.modulecore.domain.place.Place

interface PlacePort {

    fun getStores(regionNo: Int?): List<Place>

    fun getStoreDetail(storeId: String): Place?

}