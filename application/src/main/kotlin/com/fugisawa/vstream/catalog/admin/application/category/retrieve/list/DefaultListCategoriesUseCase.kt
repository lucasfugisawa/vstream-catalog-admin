package com.fugisawa.vstream.catalog.admin.application.category.retrieve.list

import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchQuery
import com.fugisawa.vstream.catalog.admin.domain.pagination.Pagination

class DefaultListCategoriesUseCase(private val categoryGateway: CategoryGateway): ListCategoriesUseCase() {
    override fun execute(input: CategorySearchQuery): Pagination<CategoryListOutput> = categoryGateway
        .findAll(input)
        .run {
            Pagination(
                current = this.current,
                size = this.size,
                total = this.total,
                items = this.items.map(::categoryToCategoryListOutput)
            )
        }
}