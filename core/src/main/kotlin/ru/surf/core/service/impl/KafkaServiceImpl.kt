package ru.surf.core.service.impl

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.KafkaException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.surf.core.configuration.KafkaTopicConfiguration
import ru.surf.core.dto.GeneralNotificationDto
import ru.surf.core.exception.ExceptionType
import ru.surf.core.service.KafkaService

@Service
class KafkaServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, GeneralNotificationDto>,
) : KafkaService {

    companion object KafkaLogger {
        val logger: Logger = LoggerFactory.getLogger(KafkaServiceImpl::class.java)
    }

    override fun sendReceivingRequestDto(generalNotificationDto: GeneralNotificationDto) {
        val receivingRequestDtoRecord =
            ProducerRecord<String, GeneralNotificationDto>(
                KafkaTopicConfiguration.TOPICS.RECEIVING_REQUEST_TOPIC,
                generalNotificationDto
            )
        kafkaTemplate.send(receivingRequestDtoRecord).completable().whenComplete { result, ex ->
            when (ex == null) {
                true -> logger.debug("Successfully send $generalNotificationDto to ${KafkaTopicConfiguration.TOPICS.RECEIVING_REQUEST_TOPIC}")
                false -> {
                    logger.error("Message sending failed with data $result")
                    throw KafkaProducerException(
                        receivingRequestDtoRecord,
                        ExceptionType.SERVICE_EXCEPTION.toString(),
                        KafkaException()
                    )
                }
            }
        }
    }

}