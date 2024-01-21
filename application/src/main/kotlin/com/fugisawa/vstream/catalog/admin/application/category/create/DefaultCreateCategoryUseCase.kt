package com.fugisawa.vstream.catalog.admin.application.category.create

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.validation.Notification

class DefaultCreateCategoryUseCase(private val categoryGateway: CategoryGateway) : CreateCategoryUseCase() {

    override fun execute(input: CreateCategoryCommand): CreateCategoryOutput {
        val newCategory = Category.newCategory(input.name, input.description, input.active)
        val notification = Notification.create()
        newCategory.validate(notification)
        if (notification.hasErrors()) {
            //
        }
        return CreateCategoryOutput.with(categoryGateway.create(newCategory))
    }

}
