package ru.surf.core.kafkaEvents.defence

import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Team
import ru.surf.core.entity.Trainee
import java.time.LocalDateTime

sealed interface DefenceEvent {
    val team: Team
    val title: String
    val date: LocalDateTime
    val trainees: List<Trainee>
    val jury: List<SurfEmployee>
}