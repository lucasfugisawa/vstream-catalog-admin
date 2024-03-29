package com.fugisawa.vstream.catalog.admin.domain.validation.handler

import com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException
import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError
import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler

class DomainExceptionValidationHandler : ValidationHandler {

    override val errors = mutableListOf<ValidationError>()

    override fun append(error: ValidationError): ValidationHandler = throw DomainException(error)

    override fun append(handler: ValidationHandler): ValidationHandler = throw DomainException(handler.errors)

    override fun validate(validation: ValidationHandler.Validation): ValidationHandler {
        try {
            validation.validate()
        } catch (e: Exception) {
            throw DomainException(ValidationError(e.message ?: ("ValidationError: $e")))
        }
        return this
    }

    override fun hasErrors(): Boolean = super.hasErrors()
}