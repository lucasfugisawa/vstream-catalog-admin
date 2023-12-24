package br.com.fugisawa.vstream.catalog.admin.domain.category

data class CategorySearchCriteria(
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