package br.com.fugisawa.vstream.catalog.admin.domain

abstract class Entity<ID: Identifier>(protected val id: ID) {

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