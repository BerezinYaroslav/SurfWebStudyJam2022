package ru.surf.mail.service.strategy

import ru.surf.mail.model.IMailEvent
import ru.surf.mail.model.EmailType

class DefaultStrategy: EmailSendStrategy {
    override fun emailType(): EmailType = EmailType.DEFAULT
    override fun sendEmail(email: IMailEvent) = Unit
}