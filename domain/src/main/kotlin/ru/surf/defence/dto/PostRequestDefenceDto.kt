package ru.surf.defence.dto

import java.time.LocalDateTime
import java.util.*


data class PostRequestDefenceDto(
    val teamId: UUID,
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val traineeIds: List<UUID>,
    val juryIds: List<UUID>,
    val zoomCreateMeetingRequestDto: ZoomCreateMeetingRequestDto
) {
}