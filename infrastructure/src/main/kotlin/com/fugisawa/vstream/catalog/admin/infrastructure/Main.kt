package com.fugisawa.vstream.catalog.admin.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.AbstractEnvironment

@SpringBootApplication
class Main

private const val DEVELOPMENT_PROFILE = "development"

fun main(args: Array<String>) {
    System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, DEVELOPMENT_PROFILE)
    runApplication<Main>(*args)
}

