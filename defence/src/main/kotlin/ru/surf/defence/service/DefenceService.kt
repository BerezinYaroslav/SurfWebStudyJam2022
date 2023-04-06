package ru.surf.defence.service

import ru.surf.core.kafkaEvents.defence.CreateDefenceEvent
import ru.surf.defence.entity.Defence

interface DefenceService {

    fun saveDefence(createDefenceEvent: CreateDefenceEvent): Defence

    fun deleteDefenceByMeetingId(zoomMeetingId: Long)

    fun getDefenceByMeetingId(zoomMeetingId: Long): Defence
}