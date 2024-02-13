package com.fugisawa.vstream.catalog.admin.domain.category

import com.fugisawa.vstream.catalog.admin.domain.pagination.Pagination

interface CategoryGateway {
    fun create(category: Category): Category
    fun deleteById(id: CategoryID)
    fun findById(id: CategoryID): Category?
    fun update(category: Category): Category
    fun findAll(query: CategorySearchQuery): Pagination<Category>
    fun existsByIds(ids: Iterable<CategoryID>): List<CategoryID>
}