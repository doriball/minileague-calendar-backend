package io.doriball.moduleapi.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class FailResponse(
    code: String,
    val message: String? = null,
    val detail: Any? = null
): CommonResponse(code=code, success=false)
