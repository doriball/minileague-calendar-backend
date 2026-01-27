package io.doriball.moduleinfrastructure.notification.adapter

import com.slack.api.Slack
import com.slack.api.model.Attachment
import com.slack.api.model.Field
import com.slack.api.webhook.Payload.PayloadBuilder
import com.slack.api.webhook.WebhookPayloads
import io.doriball.modulecore.shared.notification.model.ErrorEvent
import io.doriball.modulecore.shared.notification.model.NotificationEvent
import io.doriball.modulecore.shared.notification.port.ErrorMessageSender
import io.doriball.modulecore.shared.notification.port.MessageSender
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("dev", "prod")
@Component
class SlackMessageSender(
    @Value("\${doriball.management.webhook_url}") private val webhookUrl: String,
    private val client: Slack = Slack.getInstance(),
) : MessageSender, ErrorMessageSender {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun sendMessage(event: NotificationEvent) {
        client.send(webhookUrl, WebhookPayloads.payload { p: PayloadBuilder? ->
            p!!
                .text(event.title)
                .attachments(generateAttachments(event.messageBody))
        })
    }

    override fun sendErrorMessage(event: ErrorEvent) {
        log.error("error is occurred, send slack message")
        val messageBody = generateErrorMessageBody(event)
        sendMessage(NotificationEvent(title="예외 발생", messageBody=messageBody))
    }

    private fun generateErrorMessageBody(event: ErrorEvent): LinkedHashMap<String, String> =
        linkedMapOf(
            "환경" to event.profile,
            "서비스명" to event.moduleName,
            "요청 IP" to event.remoteAddress,
            "요청 URL" to event.requestUrl,
            "예외명" to event.exceptionName,
            "예외 메시지" to event.exceptionMessage,
            "상세 내용" to event.exceptionStackTrace
        )

    private fun generateAttachments(messageBody: Map<String, String>): List<Attachment> = listOf(
        Attachment.builder()
            .fields(messageBody.map { generateSlackField(it.key, it.value) })
            .build()
    )

    private fun generateSlackField(title: String, fieldValue: String): Field =
        Field.builder()
            .title(title)
            .value(fieldValue)
            .valueShortEnough(false)
            .build()

}