package ru.surf.mail.service

import ru.surf.mail.model.SimpleEmail

interface EmailService {
    fun sendMail(email: SimpleEmail)
    fun sendGreeting(email: SimpleEmail)
}