package ru.surf.mail.controller

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.mail.model.IMailEvent
import ru.surf.mail.service.EmailService

@Component
@KafkaListener(topics = ["core-topics"])
class EmailListener(
    val emailService: EmailService
) {

    @KafkaHandler
    fun listenForMailEvent(@Payload value: IMailEvent) {
        println(value.javaClass)
        emailService.sendEmail(value)
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}