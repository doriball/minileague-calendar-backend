package io.doriball.moduleapi.response

class PaginationResultResponse<T>(
    code: String,
    success: Boolean,
    data: PaginationResultResponseData<T>,
): CommonResponse(code, success=success) {}