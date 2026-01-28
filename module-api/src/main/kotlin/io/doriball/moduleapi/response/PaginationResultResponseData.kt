package io.doriball.moduleapi.response

class PaginationResultResponseData<T>(
    contents: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalPages: Long,
    val totalSize: Long,
) {
    val contents: List<T> = contents.ifEmpty { emptyList() }
    val size: Int = this.contents.size
}