package io.doriball.modulecore.shared.exception

class NotFoundException : RuntimeException()

class BadRequestException(
    override val message: String? = null
) : RuntimeException()