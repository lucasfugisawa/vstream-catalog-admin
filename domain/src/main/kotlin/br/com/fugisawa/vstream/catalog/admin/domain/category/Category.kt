package br.com.fugisawa.vstream.catalog.admin.domain.category

import br.com.fugisawa.vstream.catalog.admin.domain.AggregateRoot
import java.time.Clock
import java.time.Instant

class Category private constructor(
    id: CategoryID,
    val name: String,
    val description: String?,
    val active: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant,
) : AggregateRoot<CategoryID>(id) {

    companion object {
        fun newCategory(name: String, description: String, active: Boolean): Category {
            val now = Clock.systemUTC().instant()
            return Category(
                id = CategoryID.unique(),
                name = name,
                description = description,
                active = active,
                createdAt = now,
                updatedAt = now,
                deletedAt = now,
            )
        }
    }
}