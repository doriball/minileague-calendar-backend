package io.doriball.moduleapi.response

class ListResultResponse<T>(
    code: String,
    success: Boolean,
    val data: List<T>,
): CommonResponse(code, success=success) {
}