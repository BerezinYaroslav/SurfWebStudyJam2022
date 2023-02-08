package ru.surf.mail.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.surf.mail.model.GeneralNotificationDto
import ru.surf.mail.service.EmailService

@RestController
@RequestMapping("/mail")
class EmailController(
    private val emailService: EmailService
) {
    @PostMapping("/simple")
    fun sendSimpleNotification(@RequestBody email: GeneralNotificationDto) {
        emailService.sendSimpleNotification(email)
    }

    @PostMapping("/auth/greeting")
    fun sendGreeting(@RequestBody email: GeneralNotificationDto) {
        emailService.sendGreeting(email)
    }

    @PostMapping("/auth/account/activation")
    fun sendAccountActivationLink(@RequestBody email: GeneralNotificationDto) {
        emailService.sendAccountActivationLink(email)
    }

    @PostMapping("/testing")
    fun sendTestLink(@RequestBody email: GeneralNotificationDto) {
        emailService.sendTestLink(email)
    }

    @PostMapping("/testing/result")
    fun sendTestResult(@RequestBody email: GeneralNotificationDto) {
        emailService.sendTestResult(email)
    }

    @PostMapping("/offer")
    fun sendOffer(@RequestBody email: GeneralNotificationDto) {
        emailService.sendOffer(email)
    }

}