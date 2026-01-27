package io.doriball.moduleinfrastructure.notification.adapter

import io.doriball.modulecore.shared.notification.model.ErrorEvent
import io.doriball.modulecore.shared.notification.model.NotificationEvent
import io.doriball.modulecore.shared.notification.port.ErrorMessageSender
import io.doriball.modulecore.shared.notification.port.MessageSender
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("local")
@Component
class Sl4fjMessageSender : MessageSender, ErrorMessageSender {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun sendMessage(event: NotificationEvent) {
        log.info("title: {}, messageBody: {}", event.title, event.messageBody)
    }

    override fun sendErrorMessage(event: ErrorEvent) {
        log.error("""
            
        moduleName: {}
        profile: {}
        remoteAddress: {}
        requestUrl: {},
        exceptionName: {}
        exceptionMessage: {}
        stackTrace:
        {}
        """.trimIndent(),
            event.moduleName,
            event.profile,
            event.remoteAddress,
            event.requestUrl,
            event.exceptionName,
            event.exceptionMessage,
            event.exceptionStackTrace
        )
    }

}