package ru.surf.mail.controller

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.surf.mail.model.Email
import ru.surf.mail.service.EmailService

@Component
class EmailListener(
    private val emailService: EmailService
) {

    @KafkaListener(id = "greeting", topics = ["foo_topic"])
    fun listen(value: String?) {
        emailService.sendGreeting(toEmail(value))
    }

    private fun toEmail(str: String?): Email {
        TODO("Not yet implemented")
    }
}