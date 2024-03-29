package com.fugisawa.vstream.catalog

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@ActiveProfiles("test")
@ComponentScan(
    basePackages = ["com.fugisawa.vstream.catalog"],
    useDefaultFilters = false,
    includeFilters = [ComponentScan.Filter(type = FilterType.REGEX, pattern = arrayOf(".*MySQLGateway"))]
)
@DataJpaTest
@ExtendWith(MySQLCleanUpExtension::class)
@Tag("integrationTest")
annotation class MySQLGatewayTest