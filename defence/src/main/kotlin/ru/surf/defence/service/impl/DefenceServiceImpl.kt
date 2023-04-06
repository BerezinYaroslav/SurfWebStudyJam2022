package ru.surf.defence.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.surf.core.kafkaEvents.defence.CreateDefenceEvent
import ru.surf.defence.entity.Defence
import ru.surf.defence.exception.DefenceNotFoundByMeetingIdException
import ru.surf.defence.mapper.DefenceMapper
import ru.surf.defence.repository.DefenceRepository
import ru.surf.defence.service.DefenceService
import ru.surf.defence.service.KafkaService
import ru.surf.defence.service.ZoomIntegrationService
import java.time.LocalDateTime

@Service
class DefenceServiceImpl(
    private val defenceRepository: DefenceRepository,
    private val defenceMapper: DefenceMapper,
    private val zoomIntegrationService: ZoomIntegrationService,
    private val kafkaService: KafkaService
) : DefenceService {

    companion object Logger {
        val logger = LoggerFactory.getLogger(DefenceServiceImpl::class.java)
    }

    override fun saveDefence(createDefenceEvent: CreateDefenceEvent): Defence {
        val zoomMeeting = zoomIntegrationService.createZoomMeeting(createDefenceEvent.zoomCreateMeetingRequestDto)
        try {
            defenceMapper.convertCreateDefenceKafkaEventToListNotificationMailEvents(createDefenceEvent)
                .forEach {
                    it.zoomLink = zoomMeeting.joinUrl
                    kafkaService.sendCoreEvent(it)
                }
        } catch (e: Exception) {
            deleteDefenceByMeetingId(zoomMeetingId = zoomMeeting.id)
        }
        // TODO: 10.03.2023 Написать маппер как будет готова финальная модель
        return defenceRepository.save(
            Defence(
                title = createDefenceEvent.title,
                description = createDefenceEvent.description,
                zoomLink = zoomMeeting.joinUrl,
                date = createDefenceEvent.date,
                createdAt = LocalDateTime.now(),
                zoomMeetingId = zoomMeeting.id
            )
        )
    }

    override fun deleteDefenceByMeetingId(zoomMeetingId: Long) =
        defenceRepository.delete(getDefenceByMeetingId(zoomMeetingId)).run {
            logger.info("Successfully delete defence with meeting id $zoomMeetingId")
        }

    override fun getDefenceByMeetingId(zoomMeetingId: Long): Defence {
        val defenceFromDb = (defenceRepository.findDefenceByZoomMeetingId(zoomMeetingId)
            ?: throw DefenceNotFoundByMeetingIdException(zoomMeetingId))
        logger.info("Successfully getting defence with meeting id $zoomMeetingId")
        return defenceFromDb
    }
}

