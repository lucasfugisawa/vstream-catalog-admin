package com.fugisawa.vstream.catalog.admin.application.category.update

import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID

data class UpdateCategoryCommand(
    val id: CategoryID,
    val name: String,
    val description: String?,
    val active: Boolean,
)