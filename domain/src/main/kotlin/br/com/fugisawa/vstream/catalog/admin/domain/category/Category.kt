package br.com.fugisawa.vstream.catalog.admin.domain.category

import br.com.fugisawa.vstream.catalog.admin.domain.AggregateRoot
import br.com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler
import java.time.Clock
import java.time.Instant

class Category private constructor(
    id: CategoryID,
    var name: String,
    var description: String?,
    var active: Boolean,
    val createdAt: Instant,
    var updatedAt: Instant,
    var deletedAt: Instant?,
) : AggregateRoot<CategoryID>(id) {

    companion object {
        fun newCategory(name: String, description: String?, active: Boolean): Category {
            val now = Clock.systemUTC().instant()
            val deletedAt = if (active) null else now
            return Category(
                id = CategoryID.unique(),
                name = name,
                description = description,
                active = active,
                createdAt = now,
                updatedAt = now,
                deletedAt = deletedAt,
            )
        }
    }

    override fun validate(handler: ValidationHandler) = CategoryValidator(this, handler).validate()

    fun deactivate() = apply {
        if (active) {
            val now = Clock.systemUTC().instant()
            deletedAt = now
            updatedAt = now
            active = false
        }
    }

    fun activate() = apply {
        if (!active) {
            deletedAt = null
            updatedAt = Clock.systemUTC().instant()
            active = true
        }
    }

    fun update(name: String, description: String?, active: Boolean) = apply {
        if (active) activate() else deactivate()
        this.name = name
        this.description = description
        updatedAt = Clock.systemUTC().instant()
    }
}