package com.fugisawa.vstream.catalog.admin.application.utils

sealed class Either<L, R> {

    data class Left<L, R>(override val value: L) : Either<L, R>()
    data class Right<L, R>(override val value: R) : Either<L, R>()

    abstract val value: Any?
    val isLeft: Boolean = this is Left<L, R>
    val isRight: Boolean = this is Right<L, R>
    fun leftOrNull(): L? = if (isLeft) value as L else null
    fun rightOrNull(): R? = if (isRight) value as R else null
    fun leftOrThrow(throwable: Throwable): L = if (isLeft) value as L else throw throwable
    fun rightOrThrow(throwable: Throwable): R = if (isRight) value as R else throw throwable
    fun leftOrDefault(defaultValue: L?): L? = if (isLeft) value as L else defaultValue
    fun rightOrDefault(defaultValue: R?): R? = if (isRight) value as R else defaultValue
    fun leftOrElse(onRight: (right: R) -> L?): L? = if (isLeft) value as L else onRight(value as R)
    fun rightOrElse(onLeft: (left: L) -> R?): R? = if (isRight) value as R else onLeft(value as L)

    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T = when (this) {
        is Left -> fnL(value)
        is Right -> fnR(value)
    }
}
