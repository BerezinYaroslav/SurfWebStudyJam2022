package ru.surf.mail.service.strategy

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.thymeleaf.spring5.SpringTemplateEngine
import ru.surf.mail.model.IMailEvent
import ru.surf.mail.model.EmailType
import ru.surf.mail.model.Template
import ru.surf.mail.model.dto.CandidateEventNotificationDto
import ru.surf.mail.model.dto.GeneralNotificationDto

@Component
class EventStartNotificationStrategy(
    private val javaMailSender: JavaMailSender,
    private val springTemplateEngine: SpringTemplateEngine,
) : EmailSendStrategy {
    override fun emailType(): EmailType = EmailType.EVENT_START_NOTIFICATION

    override fun sendEmail(email: IMailEvent) {
        if (email is CandidateEventNotificationDto) {
            email.eventsName.forEach {
                val message = createMimeMessage(
                    GeneralNotificationDto(
                        emailType = EmailType.DEFAULT,
                        emailTo = email.emailTo,
                        subject = email.subject,
                        notificationParams = mapOf("eventName" to it)
                    ),
                    Template.EVENT_START_NOTIFICATION.html,
                    javaMailSender,
                    springTemplateEngine
                )
                javaMailSender.send(message)
            }
        }
    }
}
