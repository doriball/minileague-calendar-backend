package io.doriball.moduleadmin.event.adapter.`in`.web

import io.doriball.moduleadmin.event.application.port.`in`.EventUseCase
import io.doriball.moduleadmin.event.application.port.`in`.dto.ReadEventsCommand
import io.doriball.moduleadmin.event.common.enums.EventKeywordSearchType
import io.doriball.moduleadmin.store.application.port.`in`.StoreRegionUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class EventPageController(
    val eventUseCase: EventUseCase,
    val storeRegionUseCase: StoreRegionUseCase
) {

    @GetMapping("/events")
    fun getEvents(command: ReadEventsCommand, modelMap: ModelMap): String {
        val events = eventUseCase.getEvents(command)
        val searchTypes = mapOf(
            "이벤트명" to EventKeywordSearchType.EVENT,
            "매장명" to EventKeywordSearchType.STORE
        )
        val storeRegions = storeRegionUseCase.getStoreRegions()

        modelMap.addAttribute("events", events)
        modelMap.addAttribute("searchTypes", searchTypes)
        modelMap.addAttribute("storeRegions", storeRegions)

        return "event/events"
    }

}