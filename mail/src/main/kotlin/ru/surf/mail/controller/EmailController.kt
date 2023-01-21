package ru.surf.mail.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.mail.model.SimpleEmail
import ru.surf.mail.service.EmailService

@RestController
@RequestMapping("/mail")
class EmailController(
    private val emailService: EmailService
) {
    @PostMapping("/simple")
    fun sendSimpleEmail(@RequestBody email: SimpleEmail) {
        emailService.sendMail(email)
    }
}