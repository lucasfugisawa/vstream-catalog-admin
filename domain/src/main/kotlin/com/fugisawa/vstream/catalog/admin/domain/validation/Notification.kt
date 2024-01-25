package com.fugisawa.vstream.catalog.admin.domain.validation

import com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException

class Notification private constructor(error: ValidationError? = null) : ValidationHandler {

    private val _errors: MutableList<ValidationError> = mutableListOf()

    companion object {
        fun create(): Notification = Notification()
        fun create(error: ValidationError? = null): Notification = Notification(error)
        fun create(error: Throwable? = null): Notification = Notification(ValidationError(error?.message ?: ""))
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

    init {
        error?.let { append(error) }
    }
}