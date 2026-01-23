package io.doriball.moduleadmin.place.adapter.`in`.web

import io.doriball.moduleadmin.place.application.port.`in`.PlaceRegionUseCase
import io.doriball.moduleadmin.place.application.port.`in`.PlaceUseCase
import io.doriball.moduleadmin.place.application.port.`in`.dto.ReadPlacesCommand
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.PlaceType
import io.doriball.modulecore.util.PaginationUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PlacePageController(
    val placeUseCase: PlaceUseCase,
    val placeRegionUseCase: PlaceRegionUseCase
) {

    @GetMapping("/places")
    fun getPlaces(command: ReadPlacesCommand, modelMap: ModelMap): String {
        val (places, totalSize) = placeUseCase.getPlaces(command)
        val totalPages = PaginationUtil.calculateTotalPages(command.size ?: 10, totalSize)
        val placeRegions = placeRegionUseCase.getPlaceRegions()
        val eventCategories = mapOf(
            "공인" to LeagueCategoryType.OFFICIAL,
            "비공인" to LeagueCategoryType.UNOFFICIAL,
            "이벤트" to LeagueCategoryType.EVENT,
            "코리안리그" to LeagueCategoryType.KOREAN_LEAGUE,
        )
        val placeTypes = mapOf(
            "매장" to PlaceType.STORE,
            "기타" to PlaceType.ETC
        )
        val paginationBars = PaginationUtil.getPaginationBarNumbers(command.page ?: 1, totalPages.toInt())

        modelMap.addAttribute("places", places)
        modelMap.addAttribute("placeRegions", placeRegions)
        modelMap.addAttribute("eventCategories", eventCategories)
        modelMap.addAttribute("placeTypes", placeTypes)
        modelMap.addAttribute("paginationBars", paginationBars)
        modelMap.addAttribute("command", command)

        return "place/places"
    }

}