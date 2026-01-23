package io.doriball.moduleadmin.place.adapter.`in`.web

import io.doriball.moduleadmin.place.application.dto.PlaceDetailDto
import io.doriball.moduleadmin.place.application.dto.PlaceEventRuleDto
import io.doriball.moduleadmin.place.application.dto.PlaceSummaryDto
import io.doriball.moduleadmin.place.application.port.`in`.PlaceEventRuleUseCase
import io.doriball.moduleadmin.place.application.port.`in`.PlaceUseCase
import io.doriball.moduleadmin.place.application.port.`in`.dto.*
import io.doriball.moduleapi.response.CommonResponse
import io.doriball.moduleapi.response.ListResultResponse
import io.doriball.moduleapi.response.SingleResultResponse
import io.doriball.moduleapi.util.APIResponseUtil
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class PlaceRestController(
    val placeUseCase: PlaceUseCase,
    val placeEventRuleUseCase: PlaceEventRuleUseCase
) {

    companion object {
        const val BASE_PATH = "/api/v1/places"
    }

    @GetMapping("$BASE_PATH/summaries")
    fun getPlaceSummaries(command: ReadPlaceSummariesCommand): ListResultResponse<PlaceSummaryDto> {
        val result = placeUseCase.getPlaceSummaries(command)
        return APIResponseUtil.listResultResponse(result)
    }

    @GetMapping("$BASE_PATH/{placeId}")
    fun getPlaceDetail(@PathVariable placeId: String): SingleResultResponse<PlaceDetailDto> {
        val result = placeUseCase.getPlaceDetail(ReadPlaceDetailCommand(placeId))
        return APIResponseUtil.singeResultResponse(result)
    }

    @PostMapping(BASE_PATH)
    fun createPlace(@Valid @RequestBody command: CreatePlaceCommand): CommonResponse {
        placeUseCase.createPlace(command)
        return APIResponseUtil.successResponse()
    }

    @PutMapping("$BASE_PATH/{placeId}")
    fun updatePlace(@PathVariable placeId: String, @Valid @RequestBody command: UpdatePlaceCommand): CommonResponse {
        placeUseCase.updatePlace(placeId, command)
        return APIResponseUtil.successResponse()
    }

    @DeleteMapping("$BASE_PATH/{placeId}")
    fun deletePlace(@PathVariable placeId: String): CommonResponse {
        placeUseCase.deletePlace(placeId)
        return APIResponseUtil.successResponse()
    }

    @GetMapping("$BASE_PATH/{placeId}/rules")
    fun getPlaceEventRules(@PathVariable placeId: String): ListResultResponse<PlaceEventRuleDto> {
        val result = placeEventRuleUseCase.getPlaceEventRules(ReadPlaceEventRulesCommand(placeId))
        return APIResponseUtil.listResultResponse(result)
    }

    @PostMapping("$BASE_PATH/{placeId}/rules")
    fun createPlaceEventRule(
        @PathVariable placeId: String,
        @Valid @RequestBody command: CreatePlaceEventRuleCommand
    ): CommonResponse {
        placeEventRuleUseCase.createPlaceEventRule(placeId, command)
        return APIResponseUtil.successResponse()
    }

    @PutMapping("$BASE_PATH/{placeId}/rules/{ruleId}")
    fun updatePlaceEventRule(
        @PathVariable placeId: String,
        @PathVariable ruleId: String,
        @Valid @RequestBody command: UpdatePlaceEventRuleCommand
    ): CommonResponse {
        placeEventRuleUseCase.updatePlaceEventRule(placeId, ruleId, command)
        return APIResponseUtil.successResponse()
    }

    @DeleteMapping("$BASE_PATH/{placeId}/rules/{ruleId}")
    fun deletePlaceEventRule(@PathVariable placeId: String, @PathVariable ruleId: String): CommonResponse {
        placeEventRuleUseCase.deletePlaceEventRule(placeId, ruleId)
        return APIResponseUtil.successResponse()
    }

}