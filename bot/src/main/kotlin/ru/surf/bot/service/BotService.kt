package ru.surf.bot.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.surf.bot.configuration.BotConfigurationProperties
import ru.surf.core.kafkaEvents.bot.BotEvent

@Service
class BotService(
    private val botConfigurationProperties: BotConfigurationProperties,
    private val strategyHandler: StrategyHandler
) : TelegramLongPollingBot(botConfigurationProperties.token) {

    override fun getBotUsername(): String = botConfigurationProperties.botName

    var neededChat: String = ""

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            neededChat = chatId.toString()
            val responseText = if (message.hasText()) {
                val messageText = message.text
                when {
                    messageText == "/start" -> "Привет, я бот-помощник компании Surf!"
                    messageText == "1" -> "Че как"
                    messageText == "2" -> "Ыыы"
//                    messageText.startsWith("Кнопка ") -> "Вы нажали кнопку"
                    else -> "Вы написали: *$messageText*"
                }
            } else "Я понимаю только текст"
            sendAnswer(neededChat, responseText)
        }
    }

    fun sendAnswer(chatId: String, answer: String) =
        SendMessage(neededChat, answer).also { execute(it) }

    fun sendGeneralNotification(botEvent: BotEvent) =
        SendMessage(neededChat, strategyHandler.chooseStrategy(botEvent)) .also { execute(it) }

}