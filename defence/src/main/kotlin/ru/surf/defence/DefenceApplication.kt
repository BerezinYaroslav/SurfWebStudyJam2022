package ru.surf.defence


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import ru.surf.defence.configuration.KafkaConsumerPropertiesConfiguration
import ru.surf.defence.configuration.KafkaProducerPropertiesConfiguration
import ru.surf.defence.configuration.ZoomAdminPropertiesConfiguration
import ru.surf.defence.configuration.ZoomPropertiesConfiguration

@EnableConfigurationProperties(
    ZoomPropertiesConfiguration::class,
    KafkaProducerPropertiesConfiguration::class,
    KafkaConsumerPropertiesConfiguration::class,
    ZoomAdminPropertiesConfiguration::class
)
@EnableScheduling
@SpringBootApplication
class DefenceApplication

fun main(args: Array<String>) {
    runApplication<DefenceApplication>(*args)
}