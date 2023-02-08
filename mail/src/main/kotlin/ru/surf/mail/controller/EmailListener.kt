package ru.surf.mail.controller

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.mail.model.GeneralNotificationDto
import ru.surf.mail.model.TopicTemplate
import ru.surf.mail.service.EmailService

@Component
class EmailListener(
    private val emailService: EmailService,
) {
    @KafkaListener(
        groupId = "1", topics = [TopicTemplate.SIMPLE_NOTIFICATION_TOPIC]
    )
    fun listenForSimpleNotification(@Payload value: GeneralNotificationDto) {
        print(value)
        emailService.sendGreeting(value)
    }
}