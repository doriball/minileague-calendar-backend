package io.doriball.modulecalander.store.adapter.`in`.web

import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import io.doriball.modulecalander.store.application.dto.StoreDetailDto
import io.doriball.modulecalander.store.application.dto.StoreDto
import io.doriball.modulecalander.store.application.port.`in`.StoreUseCase
import io.doriball.modulecalander.store.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalander.store.application.port.`in`.dto.ReadStoresCommand
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(val storeUseCase: StoreUseCase) {

    companion object {
        const val BASE_PATH = "/api/v1/stores"
    }

    @GetMapping(BASE_PATH)
    fun getStores(command: ReadStoresCommand): ListResultResponse<StoreDto> {
        val result = storeUseCase.getStores(command)
        return APIResponseUtil.listResultResponse(result)
    }

    @GetMapping("$BASE_PATH/{storeId}")
    fun getStoreDetail(@PathVariable storeId: String): SingleResultResponse<StoreDetailDto> {
        val result = storeUseCase.getStoreDetail(ReadStoreDetailCommand(storeId))
        return APIResponseUtil.singeResultResponse(result)
    }

}