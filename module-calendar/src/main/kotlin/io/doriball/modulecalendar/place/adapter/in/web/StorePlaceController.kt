package io.doriball.modulecalendar.place.adapter.`in`.web

import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecalendar.place.application.dto.StoreDetailDto
import io.doriball.modulecalendar.place.application.dto.StoreDto
import io.doriball.modulecalendar.place.application.port.`in`.PlaceUseCase
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoresCommand
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StorePlaceController(val placeUseCase: PlaceUseCase) {

    companion object {
        const val BASE_PATH = "/v1/stores"
    }

    @GetMapping(BASE_PATH)
    fun getStores(command: ReadStoresCommand): ListResultResponse<StoreDto> {
        val result = placeUseCase.getStores(command)
        return APIResponseUtil.listResultResponse(result)
    }

    @GetMapping("$BASE_PATH/{storeId}")
    fun getStoreDetail(@PathVariable storeId: String): SingleResultResponse<StoreDetailDto> {
        val result = placeUseCase.getStoreDetail(ReadStoreDetailCommand(storeId))
        return APIResponseUtil.singeResultResponse(result)
    }

}