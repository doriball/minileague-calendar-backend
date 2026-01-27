package io.doriball.modulecore.shared.notification.port

import io.doriball.modulecore.shared.notification.model.NotificationEvent

interface MessageSender {

    fun sendMessage(event: NotificationEvent)

}