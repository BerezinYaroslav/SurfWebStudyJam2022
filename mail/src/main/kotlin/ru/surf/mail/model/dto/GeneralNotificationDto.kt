package ru.surf.mail.model.dto

import ru.surf.mail.model.EmailType
import ru.surf.mail.model.IMailEvent

data class GeneralNotificationDto(
    override val emailType: EmailType,
    override val emailTo: String,
    override val subject: String,
    val notificationParams: Map<String, *>
) : IMailEvent {
    override fun params(): Map<String, *> {
        return notificationParams
    }
}