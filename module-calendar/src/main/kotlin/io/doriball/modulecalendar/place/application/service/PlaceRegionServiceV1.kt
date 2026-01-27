package io.doriball.modulecalendar.place.application.service

import io.doriball.modulecalendar.place.application.dto.PlaceRegionDto
import io.doriball.modulecalendar.place.application.port.`in`.PlaceRegionUseCase
import io.doriball.modulecalendar.place.application.port.out.PlaceRegionPort
import io.doriball.modulecore.shared.codes.SharedCacheName
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class PlaceRegionServiceV1(private val placeRegionPort: PlaceRegionPort) : PlaceRegionUseCase {

    @Cacheable(value = [SharedCacheName.REGIONS], unless = "#result.isEmpty()")
    override fun getPlaceRegions(): List<PlaceRegionDto> =
        placeRegionPort.getPlaceRegions().map { PlaceRegionDto.of(it) }

}