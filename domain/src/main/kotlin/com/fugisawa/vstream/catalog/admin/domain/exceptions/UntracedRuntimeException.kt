package com.fugisawa.vstream.catalog.admin.domain.exceptions

open class UntracedRuntimeException(message: String, cause: Throwable?): RuntimeException(message, cause, true, false) {
    constructor(message: String): this(message, null)
}