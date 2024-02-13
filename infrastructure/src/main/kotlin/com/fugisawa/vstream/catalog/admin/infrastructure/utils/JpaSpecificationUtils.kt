package com.fugisawa.vstream.catalog.admin.infrastructure.utils

import org.springframework.data.jpa.domain.Specification

fun <T> like(prop: String, term: String): Specification<T> =
    Specification { root, _, cb ->
        cb.like(
            cb.upper(root.get(prop)),
            term.uppercase().let { "%$it%" }
        )
    }