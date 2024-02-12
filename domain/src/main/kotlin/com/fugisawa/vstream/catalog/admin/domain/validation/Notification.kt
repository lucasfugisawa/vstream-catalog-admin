package com.fugisawa.vstream.catalog.admin.domain.validation

import com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException

class Notification(error: ValidationError? = null) : ValidationHandler {

    private val validationErrors: MutableList<ValidationError> = mutableListOf()

    constructor(error: Throwable? = null) : this(ValidationError(error?.message ?: ""))
    constructor() : this(null as ValidationError?)

    override val errors: List<ValidationError>
        get() = validationErrors.toList()

    override fun append(error: ValidationError): ValidationHandler {
        validationErrors += error
        return this
    }

    override fun append(handler: ValidationHandler): ValidationHandler {
        validationErrors += handler.errors
        return this
    }

    override fun validate(validation: ValidationHandler.Validation): ValidationHandler {
        try {
            validation.validate()
        } catch (e: DomainException) {
            validationErrors += e.errors
        } catch (e: Throwable) {
            validationErrors += ValidationError(e.message ?: "")
        }
        return this
    }

    init {
        error?.let { append(error) }
    }
}