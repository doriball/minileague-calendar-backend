package io.doriball.moduleadmin.place.application.service

import io.doriball.moduleadmin.place.application.dto.PlaceEventRuleDto
import io.doriball.moduleadmin.place.application.port.`in`.PlaceEventRuleUseCase
import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceEventRuleCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.ReadPlaceEventRulesCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceEventRuleCommand
import io.doriball.moduleadmin.place.application.port.out.PlaceEventRulePort
import io.doriball.moduleadmin.place.domain.PlaceEventRuleCreate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleUpdate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceEventRuleServiceV1(val placeEventRulePort: PlaceEventRulePort): PlaceEventRuleUseCase {

    override fun getPlaceEventRules(command: ReadPlaceEventRulesCommand): List<PlaceEventRuleDto> {
        val rules = placeEventRulePort.getPlaceEventRules(command.placeId)
        return rules.map { PlaceEventRuleDto.from(it) }
    }

    @Transactional
    override fun createPlaceEventRule(placeId: String, command: CreatePlaceEventRuleCommand) {
        placeEventRulePort.createPlaceEventRule(PlaceEventRuleCreate.from(placeId, command))
    }

    @Transactional
    override fun updatePlaceEventRule(placeId: String, ruleId: String, command: UpdatePlaceEventRuleCommand) {
        placeEventRulePort.updatePlaceEventRule(PlaceEventRuleUpdate.from(placeId, ruleId, command))
    }

    @Transactional
    override fun deletePlaceEventRule(placeId: String, ruleId: String) {
        placeEventRulePort.deletePlaceEventRule(placeId, ruleId)
    }

}