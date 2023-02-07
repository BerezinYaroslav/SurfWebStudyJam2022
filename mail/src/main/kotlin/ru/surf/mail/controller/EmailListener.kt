package ru.surf.mail.controller

import org.springframework.stereotype.Component
import ru.surf.mail.model.Email
import ru.surf.mail.service.EmailService

@Component
class EmailListener(
    private val emailService: EmailService
) {

//    @KafkaListener(groupId = "1", topics = ["greeting"])
//    fun listen(value: Email) {
//        emailService.sendGreeting(value)
//    }

    private fun toEmail(str: String?): Email {
        TODO("Not yet implemented")
    }
}