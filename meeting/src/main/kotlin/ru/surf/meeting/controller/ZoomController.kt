package ru.surf.meeting.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.surf.meeting.dto.zoom.ZoomAdminUserResponse
import ru.surf.meeting.service.ZoomIntegrationService
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@RestController
@RequestMapping("/zoom")
class ZoomController(private val zoomIntegrationService: ZoomIntegrationService) {

    companion object Logger{
        val logger = LoggerFactory.getLogger(ZoomController::class.java)
    }

    @GetMapping("/{id}")
    fun getAdminUser(@PathVariable(name = "id") id: String): Mono<ZoomAdminUserResponse> {
        return Mono.just(zoomIntegrationService.getZoomAdminUserInformation(id))
    }

    @GetMapping("/test")
    fun get():String{
        logger.info("Hello ${ThreadLocalRandom.current().nextInt()}")
        return "test"
    }

}