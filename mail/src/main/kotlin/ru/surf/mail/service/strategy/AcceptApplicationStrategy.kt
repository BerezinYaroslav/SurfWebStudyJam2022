package ru.surf.mail.service.strategy

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.thymeleaf.spring5.SpringTemplateEngine
import ru.surf.mail.model.IMailEvent
import ru.surf.mail.model.EmailType
import ru.surf.mail.model.Template

@Component
class AcceptApplicationStrategy(
    private val javaMailSender: JavaMailSender,
    private val springTemplateEngine: SpringTemplateEngine,
) : EmailSendStrategy {

    override fun emailType(): EmailType = EmailType.ACCEPT_APPLICATION

    override fun sendEmail(email: IMailEvent) {
        val message = createMimeMessage(email, Template.ACCEPT_APPLICATION.html, javaMailSender, springTemplateEngine)
        javaMailSender.send(message)
    }
}