package io.doriball.modulecalendar.place.application.dto

import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.modulecore.domain.enums.DayOfWeekType

class StoreDetailDto(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
    val map: String?,
    val sns: String?,
    val events: Map<DayOfWeekType, List<StoreEventRuleDto>>,
) {

    companion object {
        fun from(place: Place, placeEventRules: List<PlaceEventRule>): StoreDetailDto {
            val rulesByDay = placeEventRules.groupBy(
                { it.dayOfWeek },
                { StoreEventRuleDto.from(it) }
            )

            val eventsMap = DayOfWeekType.entries.associateWith { day ->
                rulesByDay[day] ?: emptyList()
            }

            return StoreDetailDto(
                id = place.id,
                name = place.name,
                region = place.region.name,
                address = place.address,
                map = place.mapInformation,
                sns = place.sns,
                events = eventsMap
            )
        }
    }

}