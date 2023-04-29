package ru.surf.bot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.bot.service.SurfHelperBot

@RestController
@RequestMapping("/bot")
class BotController {
    val surfHelperBot = SurfHelperBot("5920580392:AAGmKPcSneo2KpEXA0G_dCQuhW6bayazv7g")

    @PostMapping("/send/{chatId}/{message}")
    fun sendHello(@PathVariable chatId: Long, @PathVariable message: String) {
        surfHelperBot.sendNotification(chatId, message)
    }

    @GetMapping
    fun get() {

    }
}