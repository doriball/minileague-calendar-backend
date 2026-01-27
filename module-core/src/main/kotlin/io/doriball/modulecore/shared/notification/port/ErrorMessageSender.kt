package io.doriball.modulecore.shared.notification.port

import io.doriball.modulecore.shared.notification.model.ErrorEvent

interface ErrorMessageSender {

    fun sendErrorMessage(event: ErrorEvent)

}