package io.doriball.moduleadmin.event.adapter.`in`.web

import io.doriball.moduleadmin.event.application.port.`in`.EventUseCase
import io.doriball.moduleadmin.event.application.port.`in`.dto.ReadEventsCommand
import io.doriball.moduleadmin.event.common.enums.EventKeywordSearchType
import io.doriball.moduleadmin.place.application.port.`in`.PlaceRegionUseCase
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller
class EventPageController(
    private val eventUseCase: EventUseCase,
    private val placeRegionUseCase: PlaceRegionUseCase
) {

    @GetMapping("/events")
    fun getEvents(
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) month: Int?,
        @RequestParam(required = false) regionNo: Int?,
        @RequestParam(required = false) official: Boolean?,
        @RequestParam(required = false) search: EventKeywordSearchType?,
        @RequestParam(required = false) keyword: String?,
        modelMap: ModelMap
    ): String {
        val now = LocalDate.now()
        val command = ReadEventsCommand(
            year = year ?: now.year,
            month = month ?: now.monthValue,
            regionNo = regionNo,
            official = official,
            search = search,
            keyword = keyword
        )
        val events = eventUseCase.getEvents(command)
        val searchTypes = mapOf(
            "이벤트명" to EventKeywordSearchType.EVENT,
            "매장명" to EventKeywordSearchType.STORE
        )
        val placeRegions = placeRegionUseCase.getPlaceRegions()
        val eventCategories = mapOf(
            "공인" to LeagueCategoryType.OFFICIAL,
            "비공인" to LeagueCategoryType.UNOFFICIAL,
            "이벤트" to LeagueCategoryType.EVENT,
            "코리안리그" to LeagueCategoryType.KOREAN_LEAGUE,
        )

        modelMap.addAttribute("events", events)
        modelMap.addAttribute("searchTypes", searchTypes)
        modelMap.addAttribute("placeRegions", placeRegions)
        modelMap.addAttribute("eventCategories", eventCategories)
        modelMap.addAttribute("command", command)

        return "event/events"
    }

}