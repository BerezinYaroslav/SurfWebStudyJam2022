package ru.surf.bot.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Service
class SurfHelperBot(
    @Value("5920580392:AAGmKPcSneo2KpEXA0G_dCQuhW6bayazv7g")
    token: String
) : TelegramLongPollingBot(token) {

    @Value("surf_helper_bot")
    private val botName: String = ""

    override fun getBotUsername(): String = botName

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            val responseText = if (message.hasText()) {
                val messageText = message.text

                when {
                    messageText == "/start" -> "Добро пожаловать!"
                    messageText == "1" -> "Че как"
                    messageText == "2" -> "Ыыы"
//                    messageText.startsWith("Кнопка ") -> "Вы нажали кнопку"
                    else -> "Вы написали: *$messageText*"
                }
            } else "Я понимаю только текст"

//            val username = update.message.chat.userName
            println(chatId)
            sendNotification(chatId, responseText)
        }
    }

    fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.enableMarkdown(true)

//        responseMessage.replyMarkup = getReplyMarkup(
//            listOf(
//                listOf("1", "2")
//            )
//        )

        execute(responseMessage)
    }

//    private fun getReplyMarkup(allButtons: List<List<String>>): ReplyKeyboardMarkup {
//        val markup = ReplyKeyboardMarkup()

//        markup.keyboard = allButtons.map { rowButtons ->
//            val row = KeyboardRow()
//            rowButtons.forEach { rowButton -> row.add(rowButton) }
//            row
//        }
//
//        return markup
//    }

}