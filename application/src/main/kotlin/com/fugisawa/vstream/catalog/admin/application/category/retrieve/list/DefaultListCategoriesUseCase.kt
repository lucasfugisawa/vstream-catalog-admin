package com.fugisawa.vstream.catalog.admin.application.category.retrieve.list

import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchCriteria
import com.fugisawa.vstream.catalog.admin.domain.pagination.Page

class DefaultListCategoriesUseCase(private val categoryGateway: CategoryGateway): ListCategoriesUseCase() {
    override fun execute(input: CategorySearchCriteria): Page<CategoryListOutput> = categoryGateway
        .findAll(input)
        .run {
            Page(
                current = this.current,
                size = this.size,
                total = this.total,
                items = this.items.map(::categoryToCategoryListOutput)
            )
        }
}