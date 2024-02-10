package com.fugisawa.vstream.catalog.admin.domain.category

import com.fugisawa.vstream.catalog.admin.domain.AggregateRoot
import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler
import java.time.Clock
import java.time.Instant

class Category(
    name: String,
    description: String?,
    active: Boolean = true,
) : AggregateRoot<CategoryID>(CategoryID()) {

    var name: String = name; private set
    var description: String? = description; private set
    var active: Boolean = active; private set
    var createdAt: Instant private set
    var updatedAt: Instant private set
    var deletedAt: Instant? = null; private set

    init {
        val now = now()
        this.createdAt = now
        this.updatedAt = now
        this.deletedAt = if (active) null else now
    }

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
}

private fun now() = Clock.systemUTC().instant()