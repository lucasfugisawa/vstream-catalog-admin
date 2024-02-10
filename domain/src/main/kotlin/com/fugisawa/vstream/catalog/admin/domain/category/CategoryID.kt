package com.fugisawa.vstream.catalog.admin.domain.category

import com.fugisawa.vstream.catalog.admin.domain.Identifier
import java.util.UUID

data class CategoryID(val value: String): Identifier() {
    constructor() : this(UUID.randomUUID().toString().lowercase())
    constructor(id: UUID) : this(id.toString().lowercase())
}
