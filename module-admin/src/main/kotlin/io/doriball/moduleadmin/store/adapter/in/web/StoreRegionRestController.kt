package io.doriball.moduleadmin.store.adapter.`in`.web

import io.doriball.moduleadmin.store.application.dto.StoreRegionDto
import io.doriball.moduleadmin.store.application.port.`in`.StoreRegionUseCase
import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreRegionRestController(val useCase: StoreRegionUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/store-regions"
    }

    @GetMapping(BASE_PATH)
    fun getStoreRegions(): ListResultResponse<StoreRegionDto> {
        val result = useCase.getStoreRegions()
        return APIResponseUtil.listResultResponse(result)
    }

}