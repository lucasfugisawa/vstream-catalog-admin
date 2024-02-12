package com.fugisawa.vstream.catalog.admin.application.category.update

import com.fugisawa.vstream.catalog.admin.application.utils.Either
import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException
import com.fugisawa.vstream.catalog.admin.domain.validation.Notification
import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationError

class DefaultUpdateCategoryUseCase(private val categoryGateway: CategoryGateway) : UpdateCategoryUseCase() {

    override fun execute(input: UpdateCategoryCommand): Either<Notification, UpdateCategoryOutput> {
        val category = categoryGateway.findById(input.id)
            ?: throw DomainException(ValidationError("Category with ID ${input.id} not found"))

        val notification = Notification()

        category
            .update(input.name, input.description, input.active)
            .validate(notification)

        return if (notification.hasErrors()) Either.Left(notification) else updateCategoryOutput(category)
    }

    private fun updateCategoryOutput(category: Category): Either<Notification, UpdateCategoryOutput> =
        runCatching {
            categoryGateway
                .update(category)
                .let(::UpdateCategoryOutput)
        }.fold(
            onSuccess = { Either.Right(it) },
            onFailure = { Either.Left(Notification(it)) }
        )


}
