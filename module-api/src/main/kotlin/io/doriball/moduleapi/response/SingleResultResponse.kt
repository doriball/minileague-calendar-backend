package io.doriball.moduleapi.response

class SingleResultResponse<T>(
    code: String,
    success: Boolean,
    val data: T? = null
): CommonResponse(code, success=success) {
}