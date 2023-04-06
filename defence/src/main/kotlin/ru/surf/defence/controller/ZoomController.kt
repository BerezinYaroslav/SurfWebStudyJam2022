package ru.surf.defence.controller

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.surf.defence.dto.zoom.ZoomAdminUserResponse
import ru.surf.defence.service.ZoomIntegrationService
import java.util.*

@RestController
@RequestMapping("/zoom")
class ZoomController(private val zoomIntegrationService: ZoomIntegrationService) {

    @GetMapping("/{id}")
    fun getAdminUser(@PathVariable(name = "id") id: String): Mono<ZoomAdminUserResponse> {
        return Mono.just(zoomIntegrationService.getZoomAdminUserInformation(id))
    }

}