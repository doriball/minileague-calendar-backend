package io.doriball.moduleadmin.operation.adapter.`in`.web

import io.doriball.moduleadmin.operation.application.port.`in`.NoticeUseCase
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.moduleadmin.operation.common.enums.NoticeKeywordSearchType
import io.doriball.modulecore.util.PaginationUtil
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping

@Controller
class NoticePageController(val useCase: NoticeUseCase) {

    @GetMapping("/notices")
    fun getAnnouncements(command: ReadNoticesCommand, modelMap: ModelMap): String {
        val (notices, totalSize) = useCase.getNotices(command)
        val totalPages = PaginationUtil.calculateTotalPages(command.size ?: 10, totalSize)
        val searchTypes = mapOf(
            "제목" to NoticeKeywordSearchType.TITLE,
            "내용" to NoticeKeywordSearchType.CONTENT
        )
        val paginationBars = PaginationUtil.getPaginationBarNumbers(command.page ?: 1, totalPages.toInt())

        modelMap.addAttribute("notices", notices)
        modelMap.addAttribute("searchTypes", searchTypes)
        modelMap.addAttribute("paginationBars", paginationBars)
        modelMap.addAttribute("command", command)

        return "operation/notices"
    }

}