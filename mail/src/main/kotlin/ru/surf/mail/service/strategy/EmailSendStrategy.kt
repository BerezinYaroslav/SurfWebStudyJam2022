package ru.surf.mail.service.strategy

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import ru.surf.mail.model.IMailEvent
import ru.surf.mail.model.EmailType
import java.nio.charset.StandardCharsets
import javax.mail.internet.MimeMessage

interface EmailSendStrategy {
    fun emailType(): EmailType
    fun sendEmail(email: IMailEvent)

    fun createMimeMessage(
        email: IMailEvent,
        templateLocation: String,
        javaMailSender: JavaMailSender,
        springTemplateEngine: SpringTemplateEngine
    ): MimeMessage {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper =
            MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name())
        val context = Context()
        helper.setTo(email.emailTo)
        helper.setSubject(email.subject)
        context.setVariables(email.params())
        val html: String = springTemplateEngine.process(templateLocation, context)
        helper.setText(html, true)

        return message
    }
}