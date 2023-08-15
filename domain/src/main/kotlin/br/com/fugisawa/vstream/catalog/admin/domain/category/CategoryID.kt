package br.com.fugisawa.vstream.catalog.admin.domain.category

import br.com.fugisawa.vstream.catalog.admin.domain.Identifier
import java.util.UUID

data class CategoryID private constructor(val value: String): Identifier() {
    companion object {
        fun unique() = CategoryID(UUID.randomUUID().toString().lowercase())
        fun from(anId: String) = CategoryID(anId)
        fun from(anId: UUID) = CategoryID(anId.toString().lowercase())
    }
}