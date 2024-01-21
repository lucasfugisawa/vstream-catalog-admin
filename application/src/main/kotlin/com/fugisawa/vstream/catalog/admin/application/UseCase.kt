package com.fugisawa.vstream.catalog.admin.application

abstract class UseCase<IN, OUT> {
    abstract fun execute(input: IN): OUT
}