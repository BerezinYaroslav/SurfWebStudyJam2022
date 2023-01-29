package ru.surf.mail.service

import ru.surf.mail.model.Email

interface EmailService {
    fun sendMail(email: Email)
    fun sendGreeting(email: Email)
    fun sendOffer(email:Email)
    fun sendAccountActivationLink(email: Email)
    fun sendTestLink(email: Email)
    fun sendTestResult(email: Email)
}