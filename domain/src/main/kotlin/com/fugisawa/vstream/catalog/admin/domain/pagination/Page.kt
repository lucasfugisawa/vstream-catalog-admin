package com.fugisawa.vstream.catalog.admin.domain.pagination

data class Page<T>(
    val current: Int,
    val size: Int,
    val total: Long,
    val items: List<T>,
)