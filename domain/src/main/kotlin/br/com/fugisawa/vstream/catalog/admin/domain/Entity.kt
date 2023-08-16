package br.com.fugisawa.vstream.catalog.admin.domain

import br.com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler

abstract class Entity<ID: Identifier>(val id: ID) {

    abstract fun validate(handler: ValidationHandler)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Entity<*>
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Entity(id=$id)"
    }

}