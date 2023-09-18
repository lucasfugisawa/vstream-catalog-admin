package br.com.fugisawa.vstream.catalog.admin.application.category.create

import br.com.fugisawa.vstream.catalog.admin.domain.category.Category
import br.com.fugisawa.vstream.catalog.admin.domain.category.CategoryID

data class CreateCategoryOutput(
    val id: CategoryID
) {
    companion object {
        fun with(category: Category) = CreateCategoryOutput(id = category.id)
    }
}