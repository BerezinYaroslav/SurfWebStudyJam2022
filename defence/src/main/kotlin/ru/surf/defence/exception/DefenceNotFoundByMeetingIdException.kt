package ru.surf.defence.exception

import ru.surf.core.enum.ExceptionType

class DefenceNotFoundByMeetingIdException(private val id: Long) : DefenceServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "NO DEFENCE WITH MEETING ID $id"
}