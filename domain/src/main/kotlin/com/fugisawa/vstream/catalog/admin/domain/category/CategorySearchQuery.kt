package com.fugisawa.vstream.catalog.admin.domain.category

data class CategorySearchQuery(
    val page: Int,
    val pageSize: Int,
    val terms: String,
    val sort: String,
    val direction: Direction = Direction.ASC,
) {
    enum class Direction {
        ASC, DESC
    }
}