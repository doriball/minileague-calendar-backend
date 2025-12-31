package io.doriball.moduleadmin.store.adapter.`in`.web

import io.doriball.moduleadmin.store.application.dto.StoreDetailDto
import io.doriball.moduleadmin.store.application.dto.StoreEventRuleDto
import io.doriball.moduleadmin.store.application.dto.StoreSummaryDto
import io.doriball.moduleadmin.store.application.port.`in`.StoreEventRuleUseCase
import io.doriball.moduleadmin.store.application.port.`in`.StoreUseCase
import io.doriball.moduleadmin.store.application.port.`in`.dto.*
import io.doriball.moduleapi.response.CommonResponse
import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class StoreRestController(
    val storeUseCase: StoreUseCase,
    val storeEventRuleUseCase: StoreEventRuleUseCase
) {

    companion object {
        const val BASE_PATH = "/api/v1/stores"
    }

    @GetMapping("$BASE_PATH/summaries")
    fun getStoreSummaries(command: ReadStoreSummariesCommand): ListResultResponse<StoreSummaryDto> {
        val result = storeUseCase.getStoreSummaries(command)
        return APIResponseUtil.listResultResponse(result)
    }

    @GetMapping("$BASE_PATH/{storeId}")
    fun getStoreDetail(@PathVariable storeId: String): SingleResultResponse<StoreDetailDto> {
        val result = storeUseCase.getStoreDetail(ReadStoreDetailCommand(storeId))
        return APIResponseUtil.singeResultResponse(result)
    }

    @PostMapping(BASE_PATH)
    fun createStore(@Valid @RequestBody command: CreateStoreCommand): CommonResponse {
        storeUseCase.createStore(command)
        return APIResponseUtil.successResponse()
    }

    @PostMapping("$BASE_PATH/{storeId}")
    fun updateStore(@PathVariable storeId: String, @Valid @RequestBody command: UpdateStoreCommand): CommonResponse {
        storeUseCase.updateStore(storeId, command)
        return APIResponseUtil.successResponse()
    }

    @DeleteMapping("$BASE_PATH/{storeId}")
    fun deleteStore(@PathVariable storeId: String): CommonResponse {
        storeUseCase.deleteStore(storeId)
        return APIResponseUtil.successResponse()
    }

    @GetMapping("$BASE_PATH/{storeId}/rules")
    fun getStoreEventRules(@PathVariable storeId: String): ListResultResponse<StoreEventRuleDto> {
        val result = storeEventRuleUseCase.getStoreEventRules(ReadStoreEventRulesCommand(storeId))
        return APIResponseUtil.listResultResponse(result)
    }

    @PostMapping("$BASE_PATH/{storeId}/rules")
    fun createStoreEventRule(
        @PathVariable storeId: String,
        @Valid @RequestBody command: CreateStoreEventRuleCommand
    ): CommonResponse {
        storeEventRuleUseCase.createStoreEventRule(storeId, command)
        return APIResponseUtil.successResponse()
    }

    @PutMapping("$BASE_PATH/{storeId}/rules/{ruleId}")
    fun updateStoreEventRule(
        @PathVariable storeId: String,
        @PathVariable ruleId: String,
        @Valid @RequestBody command: UpdateStoreEventRuleCommand
    ): CommonResponse {
        storeEventRuleUseCase.updateStoreEventRule(storeId, ruleId, command)
        return APIResponseUtil.successResponse()
    }

    @DeleteMapping("$BASE_PATH/{storeId}/rules/{ruleId}")
    fun deleteStoreEventRule(@PathVariable storeId: String, @PathVariable ruleId: String): CommonResponse {
        storeEventRuleUseCase.deleteStoreEventRule(storeId, ruleId)
        return APIResponseUtil.successResponse()
    }

}