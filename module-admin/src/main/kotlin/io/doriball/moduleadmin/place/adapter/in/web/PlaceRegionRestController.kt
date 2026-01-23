package io.doriball.moduleadmin.place.adapter.`in`.web

import io.doriball.moduleadmin.place.application.dto.PlaceRegionDto
import io.doriball.moduleadmin.place.application.port.`in`.PlaceRegionUseCase
import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceRegionRestController(val useCase: PlaceRegionUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/place-regions"
    }

    @GetMapping(BASE_PATH)
    fun getPlaceRegions(): ListResultResponse<PlaceRegionDto> {
        val result = useCase.getPlaceRegions()
        return APIResponseUtil.listResultResponse(result)
    }

}