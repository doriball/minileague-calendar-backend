package io.doriball.moduleadmin.place.application.port.`in`

import io.doriball.moduleadmin.place.application.dto.PlaceDetailDto
import io.doriball.moduleadmin.place.application.dto.PlaceDto
import io.doriball.moduleadmin.place.application.dto.PlaceSummaryDto
import io.doriball.moduleadmin.place.application.port.`in`.dto.*

interface PlaceUseCase {

    fun getPlaces(command: ReadPlacesCommand): Pair<List<PlaceDto>, Long>

    fun getPlaceSummaries(command: ReadPlaceSummariesCommand): List<PlaceSummaryDto>

    fun getPlaceDetail(command: ReadPlaceDetailCommand): PlaceDetailDto

    fun createPlace(command: CreatePlaceCommand)

    fun updatePlace(placeId: String, command: UpdatePlaceCommand)

    fun deletePlace(placeId: String)

}