package ru.surf.core.exception.event

import ru.surf.core.exception.ExceptionType
import ru.surf.core.exception.base.NotFoundException
import java.util.UUID

class EventNotFoundByIdException(val id: UUID) : NotFoundException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "ENTITY NOT FOUND WITH ID $id"

}