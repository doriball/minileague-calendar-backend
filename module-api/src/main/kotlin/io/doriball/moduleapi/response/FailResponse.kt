package io.doriball.moduleapi.response

class FailResponse(
    code: String,
    val message: String? = null,
    val detail: Any? = null
): CommonResponse(code=code, success=false)
