package ru.surf.defence.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.defence.service.DefenceService
import java.util.*

@RestController
@RequestMapping("/def")
class DefenceController(private val defenceService: DefenceService) {

    // TODO: 10.03.2023 Сделать метод получения защиты по team_id
}