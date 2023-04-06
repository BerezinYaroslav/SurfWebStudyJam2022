package ru.surf.defence.listener

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.defence.CreateDefenceEvent
import ru.surf.core.kafkaEvents.defence.DefenceEvent
import ru.surf.defence.service.DefenceService
import ru.surf.defence.service.StrategyService

@Component
@KafkaListener(topics = ["core-topics"])
class DefenceListener(
    private val strategyService: StrategyService
) {

    @KafkaHandler
    @RetryableTopic
    fun listenForDefenceEvent(@Payload event: DefenceEvent) {
        strategyService.consumeEvent(event)
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit

}