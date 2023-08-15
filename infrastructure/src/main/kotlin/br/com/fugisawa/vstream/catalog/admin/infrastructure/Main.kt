package br.com.fugisawa.vstream.catalog.admin.infrastructure

import br.com.fugisawa.vstream.catalog.admin.application.UseCase

fun main(args: Array<String>) {
    println("Hello World: " + args)

    UseCase()
        .execute()
        .also(::println)
}