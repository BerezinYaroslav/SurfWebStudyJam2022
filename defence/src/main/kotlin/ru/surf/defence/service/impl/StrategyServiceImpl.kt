package ru.surf.defence.service.impl

import org.springframework.stereotype.Service
import ru.surf.core.kafkaEvents.defence.CreateDefenceEvent
import ru.surf.defence.service.DefenceService
import ru.surf.defence.service.StrategyService

// TODO: 10.03.2023 Сделать рефактор позже, если будет переделываться модуль под митинги

@Service
class StrategyServiceImpl(private val defenceService: DefenceService) : StrategyService {

    enum class DefenceType {
        CREATE_DEFENCE,
    }

    override fun consumeEvent(event: Any): Any =
        when (event) {
            is CreateDefenceEvent -> defenceService.saveDefence(event)
            else -> throw UnsupportedOperationException()
        }

}