package ru.surf.mail.model

data class SimpleEmail(
    val to: String,
    val subject: String,
    val context: MutableMap<String, Any>
)