package ru.surf.mail.model

data class GeneralNotificationDto(
    val to: String,
    val subject: String,
    val notificationParams: Map<String, *>
)