package com.fugisawa.vstream.catalog.admin.application.category.create

import com.fugisawa.vstream.catalog.admin.application.utils.Either
import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.validation.Notification

class DefaultCreateCategoryUseCase(private val categoryGateway: CategoryGateway) : CreateCategoryUseCase() {

    override fun execute(input: CreateCategoryCommand): Either<Notification, CreateCategoryOutput> {
        val newCategory = Category(input.name, input.description, input.active)
        val notification = Notification.create()
        newCategory.validate(notification)
        return if (notification.hasErrors()) Either.Left(notification) else createCategoryOutput(newCategory)
    }

    private fun createCategoryOutput(newCategory: Category): Either<Notification, CreateCategoryOutput> =
        runCatching {
            categoryGateway
                .create(newCategory)
                .let(CreateCategoryOutput::with)
        }.fold(
            onSuccess = { Either.Right(it) },
            onFailure = { Either.Left(Notification.create(it)) }
        )
}
