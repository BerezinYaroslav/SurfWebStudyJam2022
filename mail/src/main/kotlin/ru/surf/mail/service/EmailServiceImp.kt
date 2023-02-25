package ru.surf.mail.service

import org.springframework.stereotype.Service
import ru.surf.mail.model.IMailEvent
import ru.surf.mail.service.strategy.StrategyFactory

@Service
class EmailServiceImp(
    val strategyFactory: StrategyFactory
) : EmailService {
    override fun sendEmail(email: IMailEvent) {
        val strategy = strategyFactory.findStrategy(email.emailType)
        strategy.sendEmail(email)
    }
}