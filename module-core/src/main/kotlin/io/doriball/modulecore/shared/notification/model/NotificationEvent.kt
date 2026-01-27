package io.doriball.modulecore.shared.notification.model

data class NotificationEvent(
    val title: String,
    val messageBody: Map<String, String>
)
