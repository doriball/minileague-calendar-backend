package io.doriball.moduleadmin.store.adapter.`in`.web

import io.doriball.moduleadmin.store.application.port.`in`.StoreRegionUseCase
import io.doriball.moduleadmin.store.application.port.`in`.StoreUseCase
import io.doriball.moduleadmin.store.application.port.`in`.dto.ReadStoresCommand
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.util.PaginationUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class StorePageController(
    val storeUseCase: StoreUseCase,
    val storeRegionUseCase: StoreRegionUseCase
) {

    @GetMapping("/stores")
    fun getStores(command: ReadStoresCommand, modelMap: ModelMap): String {
        val (stores, totalSize) = storeUseCase.getStores(command)
        val totalPages = PaginationUtil.calculateTotalPages(command.size ?: 10, totalSize)
        val storeRegions = storeRegionUseCase.getStoreRegions()
        val eventCategories = mapOf(
            "공인" to LeagueCategoryType.OFFICIAL,
            "사설" to LeagueCategoryType.PRIVATE,
            "이벤트" to LeagueCategoryType.EVENT,
            "코리안리그" to LeagueCategoryType.KOREAN_LEAGUE,
        )
        val paginationBars = PaginationUtil.getPaginationBarNumbers(command.page ?: 1, totalPages.toInt())

        modelMap.addAttribute("stores", stores)
        modelMap.addAttribute("storeRegions", storeRegions)
        modelMap.addAttribute("eventCategories", eventCategories)
        modelMap.addAttribute("paginationBars", paginationBars)
        modelMap.addAttribute("command", command)

        return "store/stores"
    }

}