package com.fugisawa.vstream.catalog.admin.domain.exceptions

import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError

class DomainException private constructor(message: String, val errors: List<ValidationError>) : UntracedRuntimeException(message) {
    companion object {
        fun of(errors: List<ValidationError>) =
            DomainException(errors.map(ValidationError::message).joinToString("; "), errors)

        fun of(error: ValidationError) = DomainException(error.message, listOf(error))
    }
}