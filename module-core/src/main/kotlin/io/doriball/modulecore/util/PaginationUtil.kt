package io.doriball.modulecore.util

import kotlin.math.ceil

object PaginationUtil {

    fun calculateTotalPages(pageSize: Int, totalSize: Long): Long {
        if (pageSize <= 0) return 0L
        return ceil(totalSize.toDouble() / pageSize).toLong()
    }

}