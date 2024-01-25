package com.fugisawa.vstream.catalog.admin.domain.validation

import com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException

class Notification : ValidationHandler {

    private val _errors: MutableList<ValidationError> = mutableListOf()

    companion object {
        fun create(error: ValidationError? = null): Notification = Notification(error)
    }

    private constructor(error: ValidationError? = null) {
        error?.let { append(error) }
    }

    override val errors: List<ValidationError>
        get() = _errors.toList()

    override fun append(error: ValidationError): ValidationHandler {
        _errors += error
        return this
    }

    override fun append(handler: ValidationHandler): ValidationHandler {
        _errors += handler.errors
        return this
    }

    override fun validate(validation: ValidationHandler.Validation): ValidationHandler {
        try {
            validation.validate()
        } catch (e: DomainException) {
            _errors += e.errors
        } catch (e: Throwable) {
            _errors += ValidationError(e.message ?: "")
        }
        return this
    }
}