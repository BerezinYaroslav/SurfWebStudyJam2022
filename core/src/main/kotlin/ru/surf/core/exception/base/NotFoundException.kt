package ru.surf.core.exception.base

abstract class NotFoundException : CoreServiceException {

    override val description: String = "NOT FOUND ENTITY"

    constructor() : super()

    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

    constructor(cause: Throwable?) : super(cause)

}