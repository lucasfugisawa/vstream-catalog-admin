package com.fugisawa.vstream.catalog.admin.application.utils

sealed class Either<out L, out R> {

    data class Left<out L>(override val value: L) : Either<L, Nothing>()
    data class Right<out R>(override val value: R) : Either<Nothing, R>()

    abstract val value: Any?
    val isLeft: Boolean = this is Left<L>
    val isRight: Boolean = this is Right<R>
    fun leftOrNull(): L? = if (isLeft) value as L else null
    fun rightOrNull(): R? = if (isRight) value as R else null
    fun leftOrThrow(throwable: Throwable): L = if (isLeft) value as L else throw throwable
    fun rightOrThrow(throwable: Throwable): R = if (isRight) value as R else throw throwable

    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T = when (this) {
        is Left -> fnL(value)
        is Right -> fnR(value)
    }
}