package ru.surf.mail.model

data class Email(
    val to: String,
    val subject: String,
    val context: MutableMap<String, Any>
)