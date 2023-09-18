package br.com.fugisawa.vstream.catalog.admin.application

abstract class NullaryUseCase<OUT> {
    abstract fun execute(): OUT
}