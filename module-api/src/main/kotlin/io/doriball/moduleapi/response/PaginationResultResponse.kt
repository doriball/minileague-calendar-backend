package io.doriball.moduleapi.response

class PaginationResultResponse<T>(
    code: String,
    success: Boolean,
    contents: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalPages: Long,
    val totalSize: Long,
): CommonResponse(code, success=success) {

    val contents: List<T> = contents.ifEmpty { emptyList() }
    val size: Int = this.contents.size

}