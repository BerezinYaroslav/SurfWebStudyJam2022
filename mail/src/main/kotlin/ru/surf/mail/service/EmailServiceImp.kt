package ru.surf.mail.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import ru.surf.mail.model.Email
import ru.surf.mail.model.Templates
import java.nio.charset.StandardCharsets
import javax.mail.internet.MimeMessage

@Service
class EmailServiceImp(
    private val javaMailSender: JavaMailSender,
    private val springTemplateEngine: SpringTemplateEngine
) : EmailService {

    override fun sendMail(email: Email) {
        TODO("Not yet implemented")
    }

    override fun sendGreeting(email: Email) {
        javaMailSender.send(createMimeMessage(email, Templates.GREETING.template))
    }

    override fun sendOffer(email: Email) {
        javaMailSender.send(createMimeMessage(email, Templates.OFFER.template))
    }

    override fun sendAccountActivationLink(email: Email) {
        javaMailSender.send(createMimeMessage(email, Templates.ACCOUNT_ACTIVATION.template))
    }

    override fun sendTestLink(email: Email) {
        javaMailSender.send(createMimeMessage(email, Templates.TEST.template))
    }

    override fun sendTestResult(email: Email) {
        javaMailSender.send(createMimeMessage(email, Templates.TEST_RESULT.template))
    }

    private fun createMimeMessage(email: Email, templateLocation: String): MimeMessage {
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