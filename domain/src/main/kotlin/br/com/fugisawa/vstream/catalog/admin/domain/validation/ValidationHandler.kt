package br.com.fugisawa.vstream.catalog.admin.domain.validation

interface ValidationHandler {
    val errors: List<ValidationError>

    fun append(error: ValidationError): ValidationHandler
    fun append(handler: ValidationHandler): ValidationHandler
    fun validate(validation: Validation): ValidationHandler
    fun hasErrors(): Boolean = errors.isNotEmpty()

    interface Validation {
        fun validate()
    }
}
