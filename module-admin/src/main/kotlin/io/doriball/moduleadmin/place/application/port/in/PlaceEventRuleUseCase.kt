package io.doriball.moduleadmin.place.application.port.`in`

import io.doriball.moduleadmin.place.application.dto.PlaceEventRuleDto
import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceEventRuleCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.ReadPlaceEventRulesCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceEventRuleCommand

interface PlaceEventRuleUseCase {

    fun getPlaceEventRules(command: ReadPlaceEventRulesCommand) : List<PlaceEventRuleDto>

    fun createPlaceEventRule(placeId: String, command: CreatePlaceEventRuleCommand)

    fun updatePlaceEventRule(placeId: String, ruleId: String, command:  UpdatePlaceEventRuleCommand)

    fun deletePlaceEventRule(placeId: String, ruleId: String)

}