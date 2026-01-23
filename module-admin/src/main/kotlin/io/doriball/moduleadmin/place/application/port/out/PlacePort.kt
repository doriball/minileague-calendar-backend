package io.doriball.moduleadmin.place.application.port.out

import io.doriball.moduleadmin.place.domain.PlaceCreate
import io.doriball.moduleadmin.place.domain.PlaceSummary
import io.doriball.moduleadmin.place.domain.PlaceUpdate
import io.doriball.modulecore.domain.place.Place

interface PlacePort {

    fun getPlaces(page: Int?, size: Int?, keyword: String?, regionNo: Int?): Pair<List<Place>, Long>

    fun getPlaceSummaries(regionNo: Int): List<PlaceSummary>

    fun getPlaceDetail(placeId: String): Place?

    fun createPlace(create: PlaceCreate)

    fun updatePlace(placeId: String, update: PlaceUpdate)

    fun deletePlace(placeId: String)

    fun isEventExist(placeId: String): Boolean

}