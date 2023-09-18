package br.com.fugisawa.vstream.catalog.admin.domain.category

import br.com.fugisawa.vstream.catalog.admin.domain.Identifier
import java.util.UUID

data class CategoryID private constructor(val value: String) : Identifier() {
    companion object {
        fun unique() = CategoryID(UUID.randomUUID().toString().lowercase())
        fun from(id: String) = CategoryID(id)
        fun from(id: UUID) = CategoryID(id.toString().lowercase())
    }
}