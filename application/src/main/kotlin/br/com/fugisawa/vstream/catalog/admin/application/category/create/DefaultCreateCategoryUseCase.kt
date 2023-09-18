package br.com.fugisawa.vstream.catalog.admin.application.category.create

import br.com.fugisawa.vstream.catalog.admin.domain.category.Category
import br.com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import br.com.fugisawa.vstream.catalog.admin.domain.validation.handler.DomainExceptionValidationHandler

class DefaultCreateCategoryUseCase(private val categoryGateway: CategoryGateway) : CreateCategoryUseCase() {

    override fun execute(input: CreateCategoryCommand): CreateCategoryOutput =
        Category.newCategory(input.name, input.description, input.active)
            .also { it.validate(DomainExceptionValidationHandler()) }
            .also { categoryGateway.create(it) }
            .let { CreateCategoryOutput.with(it) }

}