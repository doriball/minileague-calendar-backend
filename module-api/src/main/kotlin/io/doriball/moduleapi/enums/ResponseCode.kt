package io.doriball.moduleapi.enums

enum class ResponseCode(
    val message: String,
    val code: String,
) {

    // 성공
    SUCCESS("success", "S001"),

    // 스프링 관련 예외
    REQUEST_METHOD_NOT_SUPPORTED("request method not supported", "E001"),
    MEDIA_TYPE_NOT_SUPPORTED("media type not supported", "E002"),
    MEDIA_TYPE_NOT_ACCEPTABLE("media type not acceptable", "E003"),
    MISSING_PATH_VARIABLE("missing path variable", "E004"),
    MISSING_SERVLET_REQUEST_PARAMETER("missing servlet request parameter", "E005"),
    MISSING_SERVLET_REQUEST_PART("missing servlet request part", "E006"),
    SERVER_REQUEST_BINDING_FAILURE("server request binding failure", "E007"),
    METHOD_ARGUMENT_NOT_VALID("method argument not valid", "E008"),
    NO_HANDLER_FOUND("no handler found", "E009"),
    ASYNC_REQUEST_TIMEOUT("async request timeout", "E010"),
    CONVERSION_NOT_SUPPORTED("conversion not supported", "E011"),
    TYPE_MISMATCH("type mismatch", "E012"),
    HTTP_MESSAGE_NOT_READABLE("http message not readable", "E013"),
    HTTP_MESSAGE_NOT_WRITABLE("http message not writable", "E014"),
    MAX_UPLOAD_SIZE_EXCEEDED("max upload size exceeded", "E015"),

    // 인증 관련
    INVALID_ACCESS_TOKEN("invalid access token", "E100"),
    INVALID_REFRESH_TOKEN("invalid refresh token", "E101"),

    // 공통 예외
    COMMON_RESOURCE_NOT_FOUND("resource not found", "E404"),
    COMMON_INVALID_REQUEST("invalid request", "E400"),

    // 기타 예외
    INTERNAL_SERVER_ERROR("internal server error", "E500")

}