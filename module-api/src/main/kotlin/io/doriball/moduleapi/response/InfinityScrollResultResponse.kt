package io.doriball.moduleapi.response

class InfinityScrollResultResponse<T>(
    code: String,
    success: Boolean,
    data: InfinityScrollResultResponseData<T>,
): CommonResponse(code, success=success) {}