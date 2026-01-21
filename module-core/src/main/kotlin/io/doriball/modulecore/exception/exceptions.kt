package io.doriball.modulecore.exception

class NotFoundException : RuntimeException()

class BadRequestException(
    override val message: String? = null
) : RuntimeException()