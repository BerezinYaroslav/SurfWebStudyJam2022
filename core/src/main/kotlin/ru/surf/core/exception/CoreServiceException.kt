package ru.surf.core.exception

class CoreServiceException : RuntimeException {

    constructor(code: CODE, message: String) : this(code, null, message)

    constructor(code: CODE, throwable: Throwable?, message: String) : super(message, throwable) {
        this.code = code
    }

    enum class CODE(val description: String) {

        CS_DATABASE_ERROR("Core service database error");

        fun awaken() = CoreServiceException(this, this.description)

        fun awaken(message: String): CoreServiceException =
            CoreServiceException(this, this.description + ": " + message)

        fun awaken(throwable: Throwable) =
            CoreServiceException(this, throwable, this.description + ":" + throwable.message)

        fun awaken(throwable: Throwable, message: String) =
            CoreServiceException(this, throwable, this.description + ":" + message)


    }

    var code: CODE

}


