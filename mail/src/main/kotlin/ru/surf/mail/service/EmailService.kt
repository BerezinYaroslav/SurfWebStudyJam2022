package ru.surf.mail.service

import ru.surf.mail.model.IMailEvent

interface EmailService {
    fun sendEmail(email: IMailEvent)
}