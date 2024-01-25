package com.fugisawa.vstream.catalog.admin.application.utils

sealed class Either<L, R> {

    data class Left<L, R>(override val value: L) : Either<L, R>()
    data class Right<L, R>(override val value: R) : Either<L, R>()

    abstract val value: Any?
    val hasLeft: Boolean = this is Left<L, R>
    val hasRight: Boolean = this is Right<L, R>
    fun leftOrNull(): L? = if (this is Left<L, R>) value else null
    fun rightOrNull(): R? = if (this is Right<L, R>) value else null
    fun leftOrThrow(throwable: Throwable): L = if (this is Left<L, R>) value else throw throwable
    fun rightOrThrow(throwable: Throwable): R = if (this is Right<L, R>) value else throw throwable
    fun leftOrDefault(defaultValue: L?): L? = if (this is Left<L, R>) value else defaultValue
    fun rightOrDefault(defaultValue: R?): R? = if (this is Right<L, R>) value else defaultValue
    fun leftOrElse(onRight: (right: R) -> L?): L? = if (this is Left<L, R>) value else onRight(value as R)
    fun rightOrElse(onLeft: (left: L) -> R?): R? = if (this is Right<L, R>) value else onLeft(value as L)

    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T = when (this) {
        is Left -> fnL(value)
        is Right -> fnR(value)
    }
}
