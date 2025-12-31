package io.doriball.modulecore.util

import kotlin.math.ceil

object PaginationUtil {

    fun calculateTotalPages(pageSize: Int, totalSize: Long): Long {
        if (pageSize <= 0) return 0L
        return ceil(totalSize.toDouble() / pageSize).toLong()
    }

    fun getPaginationBarNumbers(currentPageNumber: Int, totalPages: Int): List<Int> {
        val barLength = 10
        val startNumber = maxOf(currentPageNumber - (barLength / 2), 0)
        val endNumber = minOf(startNumber + barLength, totalPages)

        return (startNumber until endNumber).toList()
    }

}