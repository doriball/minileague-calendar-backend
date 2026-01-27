package io.doriball.modulecore.shared.notification.model

data class ErrorEvent(
    val moduleName: String,
    val profile: String,
    val remoteAddress: String,
    val requestUrl: String,
    val exceptionName: String,
    val exceptionMessage: String,
    val exceptionStackTrace: String,
)
