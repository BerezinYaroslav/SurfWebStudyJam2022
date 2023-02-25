package ru.surf.mail.service.strategy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.surf.mail.model.EmailType


@Component
class StrategyFactory {
    private var strategies : Map<EmailType, EmailSendStrategy> = mutableMapOf()

    fun findStrategy(strategyName: EmailType): EmailSendStrategy {
        return strategies.getOrDefault(strategyName, DefaultStrategy())
    }

    @Autowired
    fun createStrategies(strategySet: Set<EmailSendStrategy>) {
        strategies = strategySet.associateBy { it.emailType() }
    }
}