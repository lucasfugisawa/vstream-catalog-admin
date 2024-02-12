package com.fugisawa.vstream.catalog.admin.application.category.delete

import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID

class DefaultDeleteCategoryUseCase(private val categoryGateway: CategoryGateway) : DeleteCategoryUseCase() {

    override fun execute(input: CategoryID) {
        categoryGateway.deleteById(input)

    }
}