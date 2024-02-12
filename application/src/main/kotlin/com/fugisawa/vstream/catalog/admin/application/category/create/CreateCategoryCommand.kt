package com.fugisawa.vstream.catalog.admin.application.category.create

data class CreateCategoryCommand(
    val name: String,
    val description: String,
    val active: Boolean,
)