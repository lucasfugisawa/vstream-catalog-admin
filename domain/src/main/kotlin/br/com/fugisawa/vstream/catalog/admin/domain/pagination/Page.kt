package br.com.fugisawa.vstream.catalog.admin.domain.pagination

data class Page<T>(
    val currentPage: Int,
    val perPage: Int,
    val total: Long,
    val items: List<T>,
)