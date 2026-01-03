package io.doriball.modulecalendar.store.application.dto

import io.doriball.modulecore.domain.store.Store
import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.enums.DayOfWeekType

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
        fun from(store: Store, storeEventRules: List<StoreEventRule>): StoreDetailDto {
            val rulesByDay = storeEventRules.groupBy(
                { it.dayOfWeek },
                { StoreEventRuleDto.from(it) }
            )

            val eventsMap = DayOfWeekType.entries.associateWith { day ->
                rulesByDay[day] ?: emptyList()
            }

            return StoreDetailDto(
                id = store.id,
                name = store.name,
                region = store.region.name,
                address = store.address,
                map = store.mapInformation,
                sns = store.sns,
                events = eventsMap
            )
        }
    }

}