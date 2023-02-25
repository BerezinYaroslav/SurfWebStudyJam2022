package ru.surf.mail.model

interface IMailEvent{
    val emailType: EmailType
    val emailTo: String
    val subject: String
    fun params(): Map<String, *>
}