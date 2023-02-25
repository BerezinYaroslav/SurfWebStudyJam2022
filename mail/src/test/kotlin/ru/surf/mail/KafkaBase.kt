package ru.surf.mail

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.support.serializer.JsonSerializer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import ru.surf.mail.model.dto.GeneralNotificationDto
import java.util.*


class KafkaBase {
    companion object {
        @JvmStatic
        var kafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")).apply {
            start()
        }

        private val admin = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaContainer.bootstrapServers))

        fun createTopic(topic: String) {
            admin.createOrModifyTopics(TopicBuilder.name(topic).partitions(3).build())
        }

        fun writeToTopic(data: GeneralNotificationDto, topic: String) {
            val props = Properties()

            props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaContainer.bootstrapServers
            props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
            props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
            props[JsonSerializer.ADD_TYPE_INFO_HEADERS] = false

            val producer: KafkaProducer<String, GeneralNotificationDto> = KafkaProducer(props)

            producer.send(ProducerRecord(topic, data))
        }
    }
}