package ru.surf.meeting.mapper.defence.impl


import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.CalScale
import net.fortuna.ical4j.model.property.Version
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.CancelDefenceNotificationEvent
import ru.surf.core.kafkaEvents.CreateDefenceNotificationEvent
import ru.surf.core.kafkaEvents.EmailType
import ru.surf.core.kafkaEvents.meeting.CancelDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.CreateDefenceMeetingEvent
import ru.surf.core.kafkaEvents.meeting.MeetingEvent
import ru.surf.meeting.mapper.defence.DefenceMapper
import java.time.ZoneOffset

@Component
class DefenceMapperImpl : DefenceMapper {

    override fun convertCreateDefenceKafkaEventToListNotificationMailEvents(createDefenceEvent: CreateDefenceMeetingEvent):
            List<CreateDefenceNotificationEvent> = sequence {
        yieldAll(createDefenceEvent.candidateParticipants.map {
            CreateDefenceNotificationEvent(
                emailType = EmailType.DEFENCE_CREATE_NOTIFICATION,
                emailTo = it.email,
                subject = "Проведение защиты проекта",
                eventName = createDefenceEvent.eventName,
                firstName = it.candidate.firstName,
                lastName = it.candidate.lastName,
                zoomLink = createDefenceEvent.zoomLink,
                attachment = listOf(createAttachment(event = createDefenceEvent))
            )
        })
        yieldAll(createDefenceEvent.surfParticipants.map {
            CreateDefenceNotificationEvent(
                emailType = EmailType.DEFENCE_CREATE_NOTIFICATION,
                // TODO: 25.03.2023 Убрать наследования работника от аккаунта, чтобы можно было получить почту
                emailTo = "kir_sokolov23@mail.ru",
                subject = "Проведение защиты проекта",
                eventName = createDefenceEvent.eventName,
                // TODO: 06.03.2023 Добавить в таблицу surf_employees first_name last_name
                firstName = it.name,
                lastName = it.name,
                zoomLink = createDefenceEvent.zoomLink,
                attachment = listOf(createAttachment(event = createDefenceEvent))
            )
        })
    }.toList()

    // TODO: 21.03.2023 Подумать над тем, как убрать дуплицирование кода
    // TODO: 25.03.2023 Посмотреть паттерн шаблонный метод
    override fun convertCancelDefenceKafkaEventToListNotificationMailEvents(
        cancelDefenceMeetingEvent: CancelDefenceMeetingEvent
    ): List<CancelDefenceNotificationEvent> =
        sequence {
            yieldAll(cancelDefenceMeetingEvent.traineeParticipants.map {
                CancelDefenceNotificationEvent(
                    emailType = EmailType.DEFENCE_CANCEL_NOTIFICATION,
                    emailTo = it.email,
                    subject = "Отмена защиты проекта",
                    // TODO: 17.03.2023 Будет рефактор позже
                    eventName = "Surf Study Jam",
                    firstName = it.candidate.firstName,
                    lastName = it.candidate.lastName,
                )
            })
            yieldAll(cancelDefenceMeetingEvent.surfParticipants.map {
                // TODO: 10.03.2023 Убрать позже хардкод переменных
                CancelDefenceNotificationEvent(
                    emailType = EmailType.DEFENCE_CANCEL_NOTIFICATION,
                    emailTo = "kir_sokolov23@mail.ru",
                    subject = "Отмена защиты проекта",
                    eventName = "Surf Study Jam",
                    // TODO: 06.03.2023 Добавить в таблицу surf_employees first_name last_name
                    firstName = it.name,
                    lastName = it.name,
                )
            })
        }.toList()

    private fun createAttachment(event: MeetingEvent): ByteArray {
        val startDateTimeInMillis = event.date.toInstant(ZoneOffset.UTC).toEpochMilli()
        val endDateTimeInMillis = event.date.plusMinutes(event.duration.toLong()).toInstant(ZoneOffset.UTC).toEpochMilli()
        return Calendar().withDefaults().withComponent(
            VEvent(DateTime(startDateTimeInMillis), DateTime(endDateTimeInMillis), event.description)
        ).withProperty(Version.VERSION_2_0).withProperty(CalScale.GREGORIAN).fluentTarget
            .run {
                this.toString().toByteArray()
            }
    }

}
