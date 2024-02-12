package com.fugisawa.vstream.catalog.admin.application.category.create

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID

data class CreateCategoryOutput(
    val id: CategoryID
) {
    constructor(category: Category) : this(category.id)
}