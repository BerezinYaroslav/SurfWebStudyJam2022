package ru.surf.mail.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import ru.surf.mail.model.GeneralNotificationDto
import ru.surf.mail.model.TopicTemplate
import java.nio.charset.StandardCharsets
import javax.mail.internet.MimeMessage

@Service
class EmailServiceImp(
    private val javaMailSender: JavaMailSender,
    private val springTemplateEngine: SpringTemplateEngine
) : EmailService {

    override fun sendSimpleNotification(email: GeneralNotificationDto) {
        javaMailSender.send(createMimeMessage(email, TopicTemplate.SIMPLE_NOTIFICATION.template))
    }

    override fun sendGreeting(email: GeneralNotificationDto) {
        javaMailSender.send(createMimeMessage(email, TopicTemplate.GREETING.template))
    }

    override fun sendOffer(email: GeneralNotificationDto) {
        javaMailSender.send(createMimeMessage(email, TopicTemplate.OFFER.template))
    }

    override fun sendAccountActivationLink(email: GeneralNotificationDto) {
        javaMailSender.send(createMimeMessage(email, TopicTemplate.ACCOUNT_ACTIVATION.template))
    }

    override fun sendTestLink(email: GeneralNotificationDto) {
        javaMailSender.send(createMimeMessage(email, TopicTemplate.TEST.template))
    }

    override fun sendTestResult(email: GeneralNotificationDto) {
        javaMailSender.send(createMimeMessage(email, TopicTemplate.TEST_RESULT.template))
    }

    private fun createMimeMessage(email: GeneralNotificationDto, templateLocation: String): MimeMessage {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name())
        val context = Context()
        helper.setTo(email.to)
        helper.setSubject(email.subject)
        context.setVariables(email.notificationParams)
        val html: String = springTemplateEngine.process(templateLocation, context)
        helper.setText(html, true)

        return message
    }
 }