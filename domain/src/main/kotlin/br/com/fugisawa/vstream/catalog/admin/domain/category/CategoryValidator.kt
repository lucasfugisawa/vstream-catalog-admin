package br.com.fugisawa.vstream.catalog.admin.domain.category

import br.com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError
import br.com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler
import br.com.fugisawa.vstream.catalog.admin.domain.validation.Validator

private const val NAME_MIN_LENGTH = 3
private const val NAME_MAX_LENGTH = 255

class CategoryValidator(private val category: Category, handler: ValidationHandler) : Validator(handler) {
    override fun validate() {
        checkNameConstraints()
    }

    private fun checkNameConstraints() {
        val name = category.name.trim()
        if (name.isEmpty()) handler.append(ValidationError("'name' must not be empty or blank"))
        if (name.length < NAME_MIN_LENGTH || name.length > NAME_MAX_LENGTH)
            handler.append(ValidationError("'name' must be at between 3 and 255 characters long"))
    }
}
