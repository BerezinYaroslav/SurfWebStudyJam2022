package ru.surf.defence.mapper.impl


import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.CreateDefenceNotificationEvent
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.core.kafkaEvents.defence.CreateDefenceEvent
import ru.surf.defence.mapper.DefenceMapper

@Component
class DefenceMapperImpl : DefenceMapper {
    
    override fun convertCreateDefenceKafkaEventToListNotificationMailEvents(createDefenceEvent: CreateDefenceEvent):
            List<CreateDefenceNotificationEvent> = sequence {
        yieldAll(createDefenceEvent.trainees.map {
            CreateDefenceNotificationEvent(
                emailType = EmailType.DEFENCE_CREATE_NOTIFICATION,
                emailTo = it.email,
                subject = "Проведение защиты проекта",
                eventName = "Surf Study Jam",
                firstName = it.candidate.firstName,
                lastName = it.candidate.lastName,
                zoomLink = ""
            )
        })
        yieldAll(createDefenceEvent.jury.map {
            // TODO: 10.03.2023
            CreateDefenceNotificationEvent(
                emailType = EmailType.DEFENCE_CREATE_NOTIFICATION,
                emailTo = "kir_sokolov23@mail.ru",
                subject = "Проведение защиты проекта",
                eventName = "Surf Study Jam",
                // TODO: 06.03.2023 Добавить в таблицу surf_employees first_name last_name
                firstName = it.name,
                lastName = it.name,
                zoomLink = ""
            )
        })
    }.toList()

}