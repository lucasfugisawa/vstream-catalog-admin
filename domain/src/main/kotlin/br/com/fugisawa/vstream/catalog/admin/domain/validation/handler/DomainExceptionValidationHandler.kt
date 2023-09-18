package br.com.fugisawa.vstream.catalog.admin.domain.validation.handler

import br.com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException
import br.com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError
import br.com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler

class DomainExceptionValidationHandler : ValidationHandler {

    override val errors = mutableListOf<ValidationError>()

    override fun append(error: ValidationError): ValidationHandler = throw DomainException.of(error)

    override fun append(handler: ValidationHandler): ValidationHandler = throw DomainException.of(handler.errors)

    override fun validate(validation: ValidationHandler.Validation): ValidationHandler {
        try {
            validation.validate()
        } catch (e: Exception) {
            throw DomainException.of(ValidationError(e.message ?: ("ValidationError: $e")))
        }
        return this
    }

    override fun hasErrors(): Boolean = super.hasErrors()
}