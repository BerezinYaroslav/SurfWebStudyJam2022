package ru.surf.defence.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.kafka.consumer")
class KafkaConsumerPropertiesConfiguration(
    val clientId: String,
    val bootstrapServers: String,
    val groupId: String,
    val autoOffsetReset: String
) {
}