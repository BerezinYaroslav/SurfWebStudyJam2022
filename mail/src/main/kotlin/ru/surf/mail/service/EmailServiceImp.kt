package ru.surf.mail.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import ru.surf.mail.model.SimpleEmail
import ru.surf.mail.model.Templates
import java.nio.charset.StandardCharsets
import javax.mail.internet.MimeMessage

@Service
class EmailServiceImp(
    private val javaMailSender: JavaMailSender,
    private val springTemplateEngine: SpringTemplateEngine
) : EmailService {

    override fun sendMail(email: SimpleEmail) {
        TODO("Not yet implemented")
    }

    override fun sendGreeting(email: SimpleEmail) {
        javaMailSender.send(createMimeMessage(email, Templates.GREETING.template))
    }

    private fun createMimeMessage(email: SimpleEmail, templateLocation: String): MimeMessage {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name())
        val context = Context()
        helper.setTo(email.to)
        helper.setSubject(email.subject)
        context.setVariables(email.context)
        val html: String = springTemplateEngine.process(templateLocation, context)
        helper.setText(html, true)

        return message
    }
 }