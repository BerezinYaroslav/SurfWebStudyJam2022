package ru.surf.core.dto

import ru.surf.core.entity.EventState
import java.util.*

data class FullResponseEventDto(
        val id: UUID,
        val title: String,
        val states: Set<EventState>
)
