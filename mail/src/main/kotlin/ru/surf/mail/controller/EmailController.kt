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
    @PostMapping("/auth/greeting")
    fun sendGreeting(@RequestBody email: SimpleEmail) {
        emailService.sendGreeting(email)
    }

    @PostMapping("/auth/account/activation")
    fun sendAccountActivationLink(@RequestBody email: SimpleEmail) {
        emailService.sendMail(email)
    }

    @PostMapping("/testing")
    fun sendTestLink(@RequestBody email: SimpleEmail) {
        emailService.sendMail(email)
    }

    @PostMapping("/testing/result")
    fun sendTestResult(@RequestBody email: SimpleEmail) {
        emailService.sendMail(email)
    }

    @PostMapping("/offer")
    fun sendOffer(@RequestBody email: SimpleEmail) {
        emailService.sendMail(email)
    }

}