package ru.surf.defenceofproject.model

import java.time.LocalDateTime

class Defence(
    val id: Int,
    val group: Group,
    val jury: Jury,
    val dateTime: LocalDateTime
)