package br.com.fugisawa.vstream.catalog.admin.domain.category

data class CategorySearchCriteria(
    val page: Int,
    val perPage: Int,
    val terms: String,
    val sort: String,
    val direction: Direction,
) {
    enum class Direction {
        ASC, DESC
    }
}