package io.doriball.moduleadmin.place.application.service

import io.doriball.moduleadmin.place.application.dto.PlaceRegionDto
import io.doriball.moduleadmin.place.application.port.`in`.PlaceRegionUseCase
import io.doriball.moduleadmin.place.application.port.out.PlaceRegionPort
import org.springframework.stereotype.Service

@Service
class PlaceRegionServiceV1(private val port: PlaceRegionPort): PlaceRegionUseCase {

    override fun getPlaceRegions(): List<PlaceRegionDto> {
        return port.getPlaceRegions().map { PlaceRegionDto.from(it) }
    }

}