package ru.surf.core.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.surf.core.dto.FullResponseEventDto
import ru.surf.core.dto.PostRequestEventDto
import ru.surf.core.dto.PutRequestEventDto
import ru.surf.core.dto.ShortResponseEventDto
import ru.surf.core.service.EventService
import java.util.UUID

@RestController
@RequestMapping("/events")
class EventController(private val eventService: EventService) {

    @PostMapping("/event")
    fun createEvent(@RequestBody postRequestEventDto: PostRequestEventDto): ResponseEntity<Unit> {
        eventService.createEvent(postRequestEventDto)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}")
    fun getEvent(@PathVariable("id") eventId: UUID): ResponseEntity<FullResponseEventDto> {
        val getResponseEventDto = eventService.getEvent(eventId)
        return ResponseEntity.ok(getResponseEventDto)
    }

    @PutMapping("/{id}")
    fun deleteEvent(
        @PathVariable(name = "id") eventId: UUID,
        @RequestBody putRequestEventDto: PutRequestEventDto
    ): ResponseEntity<ShortResponseEventDto> {
        val shortResponseEventDto = eventService.updateEvent(eventId, putRequestEventDto)
        return ResponseEntity.ok(shortResponseEventDto)
    }

    @DeleteMapping("/{id}")
    fun deleteEvent(@PathVariable(name = "id") eventId: UUID): ResponseEntity<Unit> {
        eventService.deleteEvent(eventId)
        return ResponseEntity.ok().build()
    }

}