package ru.surf.mail.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import ru.surf.mail.model.SimpleEmail

@Service
class EmailService(
    private val javaMailSender: JavaMailSender
) {
    fun sendMail(email: SimpleEmail) {
        javaMailSender.send(createSimpleMessage(email))
    }

    private fun createSimpleMessage(email: SimpleEmail): SimpleMailMessage {
        val message = SimpleMailMessage()

        message.setTo(email.to)
        message.subject = email.subject
        message.text = email.text

        return message
    }
}