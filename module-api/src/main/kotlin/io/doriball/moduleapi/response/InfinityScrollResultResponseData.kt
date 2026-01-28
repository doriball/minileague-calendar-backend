package io.doriball.moduleapi.response

class InfinityScrollResultResponseData<T>(
    contents: List<T>,
    val page: Int,
    val pageSize: Int,
) {
    val hasNext: Boolean = contents.size > pageSize
    val contents: List<T> = if (hasNext) contents.take(pageSize) else contents
    val size: Int = this.contents.size
}