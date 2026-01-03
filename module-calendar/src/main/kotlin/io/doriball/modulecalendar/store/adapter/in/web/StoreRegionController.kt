package io.doriball.modulecalendar.store.adapter.`in`.web

import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecalendar.store.application.dto.StoreRegionDto
import io.doriball.modulecalendar.store.application.port.`in`.StoreRegionUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreRegionController(val storeRegionUseCase: StoreRegionUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/store-regions"
    }

    @GetMapping(BASE_PATH)
    fun getStoreRegions(): ListResultResponse<StoreRegionDto> {
        val result = storeRegionUseCase.getStoreRegions()
        return APIResponseUtil.listResultResponse(result)
    }

}