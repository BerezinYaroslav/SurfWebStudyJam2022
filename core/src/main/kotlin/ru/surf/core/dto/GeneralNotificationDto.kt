package ru.surf.core.dto

data class GeneralNotificationDto(
    val emailTo: String,
    val eventName: String,
    val params: Map<String, *>
)
