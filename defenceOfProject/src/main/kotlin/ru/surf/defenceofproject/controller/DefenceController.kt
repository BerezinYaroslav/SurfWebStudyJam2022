package ru.surf.defenceofproject.controller

import org.springframework.web.bind.annotation.*
import ru.surf.defenceofproject.model.Defence
import ru.surf.defenceofproject.service.DefenceService

@RestController
@RequestMapping(value = ["/defences"])
class DefenceController {
    val defenceService: DefenceService = DefenceService()

    @PostMapping
    fun createDefence(@RequestBody defence: Defence): Defence {
        return defenceService.createDefence(defence)
    }

    @GetMapping("/{defenceId}")
    fun findDefence(@PathVariable defenceId: Int): Defence? {
        return defenceService.findDefence(defenceId)
    }

    @GetMapping
    fun findAllDefences(): ArrayList<Defence> {
        return defenceService.findAllDefences()
    }
}