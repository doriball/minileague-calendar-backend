package io.doriball.modulecalendar.place.application.service

import io.doriball.modulecalendar.place.application.dto.PlaceRegionDto
import io.doriball.modulecalendar.place.application.port.`in`.PlaceRegionUseCase
import io.doriball.modulecalendar.place.application.port.out.PlaceRegionPort
import org.springframework.stereotype.Service

@Service
class PlaceRegionServiceV1(val placeRegionPort: PlaceRegionPort) : PlaceRegionUseCase {

    override fun getPlaceRegions(): List<PlaceRegionDto> =
        placeRegionPort.getPlaceRegions().map { PlaceRegionDto.of(it) }

}