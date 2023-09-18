package br.com.fugisawa.vstream.catalog.admin.application

abstract class UnitUseCase<IN> {
    abstract fun execute(input: IN)
}