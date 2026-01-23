package io.doriball.modulecalendar.place.application.port.out

import io.doriball.modulecore.domain.place.PlaceEventRule

interface PlaceEventRulePort {

    fun getPlaceEventRules(placeId: String): List<PlaceEventRule>

}