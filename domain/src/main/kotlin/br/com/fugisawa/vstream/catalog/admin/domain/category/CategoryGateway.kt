package br.com.fugisawa.vstream.catalog.admin.domain.category

import br.com.fugisawa.vstream.catalog.admin.domain.pagination.Page

interface CategoryGateway {
    fun create(category: Category): Category
    fun deleteById(id: CategoryID)
    fun findById(id: CategoryID): Category?
    fun update(category: Category): Category
    fun findAll(criteria: CategorySearchCriteria): Page<Category>
}