package ru.surf.mail.model

data class SimpleEmail(
    val to: String,
    val subject: String,
    val text: String
)