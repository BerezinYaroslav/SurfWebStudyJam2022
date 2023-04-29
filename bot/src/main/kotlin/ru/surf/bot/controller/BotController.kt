package ru.surf.bot.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.bot.dto.GithubWebHookDto

@RequestMapping("/bot")
@RestController
class BotController {

    @PostMapping("/commit")
    fun webHookForCommit(@RequestBody hookData: GithubWebHookDto) = println(hookData.url)

}