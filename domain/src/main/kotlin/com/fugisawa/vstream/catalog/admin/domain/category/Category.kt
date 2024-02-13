package com.fugisawa.vstream.catalog.admin.domain.category

import com.fugisawa.vstream.catalog.admin.domain.AggregateRoot
import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler
import java.time.Clock
import java.time.Instant

data class Category(
    override var id: CategoryID,
    var name: String,
    var description: String?,
    var active: Boolean = true,
    var createdAt: Instant = Instant.now(),
    var updatedAt: Instant = createdAt,
    var deletedAt: Instant? = if (active) null else createdAt,
) : AggregateRoot<CategoryID>(id) {

    constructor(name: String, description: String?, active: Boolean): this(
        id = CategoryID(),
        name = name,
        description = description,
        active = active
    )

    override fun validate(handler: ValidationHandler) = CategoryValidator(this, handler).validate()

    fun deactivate(): Category {
        if (active) {
            val now = now()
            deletedAt = now
            updatedAt = now
            active = false
        }
        return this
    }

    fun activate(): Category {
        if (!active) {
            deletedAt = null
            updatedAt = now()
            active = true
        }
        return this
    }

    fun update(name: String, description: String?, active: Boolean): Category {
        val now = now()
        if (this.active != active) {
            this.deletedAt = if (active) null else now
            this.active = active
        }
        this.name = name
        this.description = description
        this.updatedAt = now
        return this
    }

    override fun equals(other: Any?): Boolean = super.equals(other)
    override fun hashCode(): Int = super.hashCode()
}

private fun now() = Clock.systemUTC().instant()