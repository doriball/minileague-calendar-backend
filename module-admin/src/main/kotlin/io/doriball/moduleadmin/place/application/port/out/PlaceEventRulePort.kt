package io.doriball.moduleadmin.place.application.port.out

import io.doriball.moduleadmin.place.domain.PlaceEventRuleCreate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleUpdate
import io.doriball.modulecore.domain.place.PlaceEventRule

interface PlaceEventRulePort {

    fun getPlaceEventRules(placeId: String): List<PlaceEventRule>

    fun createPlaceEventRule(create: PlaceEventRuleCreate)

    fun updatePlaceEventRule(update: PlaceEventRuleUpdate)

    fun deletePlaceEventRule(placeId: String, ruleId: String)

}