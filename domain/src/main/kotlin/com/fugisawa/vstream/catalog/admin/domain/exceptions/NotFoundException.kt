package com.fugisawa.vstream.catalog.admin.domain.exceptions

import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError

class NotFoundException(
    message: String,
    errors: List<ValidationError>
): DomainException(message, errors) {
    constructor(error: ValidationError) : this(error.message, listOf(error))
}