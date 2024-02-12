package com.fugisawa.vstream.catalog.admin.application.category.retrieve.get

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import java.time.Instant

data class CategoryOutput(
    val id: CategoryID,
    val name: String,
    val description: String?,
    val active: Boolean,
    var createdAt: Instant,
    var updatedAt: Instant,
    var deletedAt: Instant? = null,
)

fun categoryToCategoryOutput(category: Category): CategoryOutput = CategoryOutput (
    id = category.id,
    name = category.name,
    description = category.description,
    active = category.active,
    createdAt = category.createdAt,
    updatedAt = category.updatedAt,
    deletedAt = category.deletedAt,
)