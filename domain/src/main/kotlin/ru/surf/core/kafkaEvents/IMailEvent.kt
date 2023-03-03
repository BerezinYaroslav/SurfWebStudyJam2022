package ru.surf.core.kafkaEvents

interface IMailEvent{
    val emailType: EmailType
    val emailTo: String
    val subject: String
    fun params(): Map<String, *>
}
