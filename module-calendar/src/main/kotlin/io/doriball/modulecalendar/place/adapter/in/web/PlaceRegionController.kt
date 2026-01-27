package io.doriball.modulecalendar.place.adapter.`in`.web

import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecalendar.place.application.dto.PlaceRegionDto
import io.doriball.modulecalendar.place.application.port.`in`.PlaceRegionUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceRegionController(private val placeRegionUseCase: PlaceRegionUseCase) {

    companion object {
        const val BASE_PATH = "/v1/store-regions"
    }

    @GetMapping(BASE_PATH)
    fun getStoreRegions(): ListResultResponse<PlaceRegionDto> {
        val result = placeRegionUseCase.getPlaceRegions()
        return APIResponseUtil.listResultResponse(result)
    }

}