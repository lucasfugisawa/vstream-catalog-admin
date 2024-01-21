package com.fugisawa.vstream.catalog.admin.domain

import com.fugisawa.vstream.catalog.admin.domain.validation.ValidationHandler

abstract class Entity<ID : Identifier>(val id: ID) {

    abstract fun validate(handler: ValidationHandler)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Entity<*>
        return id == other.id
    }

    override fun hashCode() = id.hashCode()
    override fun toString() = "Entity(id=$id)"
}