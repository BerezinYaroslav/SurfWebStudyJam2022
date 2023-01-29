package ru.surf.mail.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.mail.model.Email
import ru.surf.mail.service.EmailService

@RestController
@RequestMapping("/mail")
class EmailController(
    private val emailService: EmailService
) {
    @PostMapping("/auth/greeting")
    fun sendGreeting(@RequestBody email: Email) {
        emailService.sendGreeting(email)
    }

    @PostMapping("/auth/account/activation")
    fun sendAccountActivationLink(@RequestBody email: Email) {
        emailService.sendAccountActivationLink(email)
    }

    @PostMapping("/testing")
    fun sendTestLink(@RequestBody email: Email) {
        emailService.sendTestLink(email)
    }

    @PostMapping("/testing/result")
    fun sendTestResult(@RequestBody email: Email) {
        emailService.sendTestResult(email)
    }

    @PostMapping("/offer")
    fun sendOffer(@RequestBody email: Email) {
        emailService.sendOffer(email)
    }

}