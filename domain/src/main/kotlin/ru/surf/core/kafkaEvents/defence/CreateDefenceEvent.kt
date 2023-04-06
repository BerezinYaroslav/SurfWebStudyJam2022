package ru.surf.core.kafkaEvents.defence

import ru.surf.core.entity.SurfEmployee
import ru.surf.core.entity.Team
import ru.surf.core.entity.Trainee
import ru.surf.defence.dto.ZoomCreateMeetingRequestDto
import java.time.LocalDateTime

data class CreateDefenceEvent(
    override val team: Team,
    override val title: String,
    val description: String,
    override val date: LocalDateTime,
    override val trainees: List<Trainee>,
    override val jury: List<SurfEmployee>,
    val zoomCreateMeetingRequestDto: ZoomCreateMeetingRequestDto
) : DefenceEvent {
}