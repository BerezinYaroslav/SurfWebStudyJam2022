package ru.surf.mail.service

import ru.surf.mail.model.GeneralNotificationDto


interface EmailService {
    fun sendSimpleNotification(email: GeneralNotificationDto)
    fun sendGreeting(email: GeneralNotificationDto)
    fun sendOffer(email:GeneralNotificationDto)
    fun sendAccountActivationLink(email: GeneralNotificationDto)
    fun sendTestLink(email: GeneralNotificationDto)
    fun sendTestResult(email: GeneralNotificationDto)
}