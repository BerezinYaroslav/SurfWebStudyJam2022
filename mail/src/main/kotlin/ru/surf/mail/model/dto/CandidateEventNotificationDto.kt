package ru.surf.mail.model.dto

import ru.surf.mail.model.EmailType
import ru.surf.mail.model.IMailEvent

data class CandidateEventNotificationDto(
    override val emailType: EmailType,
    override val emailTo: String,
    override val subject: String,

    val firstName: String,
    val lastName: String,
    val eventsName: Set<String>
) : IMailEvent {
    override fun params(): Map<String, *> {
        return mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "eventsName" to eventsName
        )
    }
}