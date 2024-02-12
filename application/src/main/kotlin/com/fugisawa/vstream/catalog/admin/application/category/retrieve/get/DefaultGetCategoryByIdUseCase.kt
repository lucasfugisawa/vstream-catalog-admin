package com.fugisawa.vstream.catalog.admin.application.category.retrieve.get

import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import com.fugisawa.vstream.catalog.admin.domain.exceptions.NotFoundException
import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError

class DefaultGetCategoryByIdUseCase(private val categoryGateway: CategoryGateway) : GetCategoryByIdUseCase() {
    override fun execute(input: CategoryID): CategoryOutput =
        categoryGateway
            .findById(input)
            ?.let(::categoryToCategoryOutput)
            ?: throw NotFoundException(ValidationError("Category with ID ${input.value} not found"))
}