package ru.surf.core.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.surf.core.dto.ErrorDto
import ru.surf.core.exception.event.EventNotFoundByIdException
import java.time.LocalDateTime


@RestControllerAdvice
class CoreGlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundByIdException::class)
    fun databaseException(exception: EventNotFoundByIdException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(exception.exceptionType.toString(), exception.message, LocalDateTime.now())
        return ResponseEntity(errorDto, HttpStatus.NOT_FOUND)
    }

}