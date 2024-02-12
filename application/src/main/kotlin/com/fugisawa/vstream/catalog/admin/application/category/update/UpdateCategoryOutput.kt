package com.fugisawa.vstream.catalog.admin.application.category.update

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID

data class UpdateCategoryOutput(
    val id: CategoryID
) {
    constructor(category: Category) : this(category.id)
}