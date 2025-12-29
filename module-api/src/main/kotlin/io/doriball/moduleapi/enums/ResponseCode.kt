package io.doriball.moduleapi.enums

enum class ResponseCode(
    val code: String,
    val message: String,
) {

    SUCCESS("success", "S001"),
    INVALID_ACCESS_TOKEN("invalid access token", "E001"),
    INVALID_REFRESH_TOKEN("invalid refresh token", "E002"),

}