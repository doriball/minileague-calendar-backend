package io.doriball.moduleadmin.operation.adapter.`in`.web

import io.doriball.moduleadmin.operation.application.port.`in`.AnnouncementUseCase
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadAnnouncementsCommand
import io.doriball.moduleadmin.operation.common.enums.AnnouncementKeywordSearchType
import io.doriball.modulecore.util.PaginationUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AnnouncementPageController(val useCase: AnnouncementUseCase) {

    @GetMapping("/announcements")
    fun getAnnouncements(command: ReadAnnouncementsCommand, modelMap: ModelMap): String {
        val (announcements, totalSize) = useCase.getAnnouncements(command)
        val totalPages = PaginationUtil.calculateTotalPages(command.size ?: 10, totalSize)
        val searchTypes = mapOf(
            "제목" to AnnouncementKeywordSearchType.TITLE,
            "내용" to AnnouncementKeywordSearchType.CONTENT
        )
        val paginationBars = PaginationUtil.getPaginationBarNumbers(command.page ?: 1, totalPages.toInt())

        modelMap.addAttribute("announcements", announcements)
        modelMap.addAttribute("searchTypes", searchTypes)
        modelMap.addAttribute("paginationBars", paginationBars)

        return "operation/announcements"
    }

}