package com.fugisawa.vstream.catalog.admin.domain.exceptions

import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError

open class DomainException(message: String, val errors: List<ValidationError>) : UntracedRuntimeException(message) {
    constructor(errors: List<ValidationError>) : this(errors.map(ValidationError::message).joinToString("; "), errors)
    constructor(error: ValidationError) : this(error.message, listOf(error))
}