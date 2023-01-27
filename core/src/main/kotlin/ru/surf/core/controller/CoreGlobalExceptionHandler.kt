package ru.surf.core.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.surf.core.dto.ErrorDto
import ru.surf.core.exception.CoreServiceException
import java.time.LocalDateTime


@RestControllerAdvice
class CoreGlobalExceptionHandler {

    @ExceptionHandler(CoreServiceException::class)
    fun databaseException(exception: CoreServiceException): ResponseEntity<ErrorDto> {
        val errorDto = ErrorDto(exception.code.toString(),exception.message ?: "", LocalDateTime.now())
        return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
    }

}