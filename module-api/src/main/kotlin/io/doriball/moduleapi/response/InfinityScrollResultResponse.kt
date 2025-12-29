package io.doriball.moduleapi.response

class InfinityScrollResultResponse<T>(
    code: String,
    success: Boolean,
    contents: List<T>,
    val page: Int,
    val pageSize: Int,
): CommonResponse(code, success=success) {

    val hasNext: Boolean = contents.size > pageSize
    val contents: List<T> = if (hasNext) contents.take(pageSize) else contents
    val size: Int = this.contents.size

}