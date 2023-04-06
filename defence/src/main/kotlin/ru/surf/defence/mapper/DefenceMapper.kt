package ru.surf.defence.mapper

import ru.surf.core.kafkaEvents.CreateDefenceNotificationEvent
import ru.surf.core.kafkaEvents.defence.CreateDefenceEvent

interface DefenceMapper {

    fun convertCreateDefenceKafkaEventToListNotificationMailEvents(createDefenceEvent: CreateDefenceEvent):
            List<CreateDefenceNotificationEvent>

}