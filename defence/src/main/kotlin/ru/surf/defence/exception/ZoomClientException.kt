package ru.surf.defence.exception

import ru.surf.core.enum.ExceptionType

class ZoomClientException(private val details: String) : DefenceServiceException() {

    val exceptionType = ExceptionType.SERVICE_EXCEPTION

    override val message: String
        get() = description

    public override val description: String = "AN ZOOM CLIENT EXCEPTION OCCURRED:$details"
}