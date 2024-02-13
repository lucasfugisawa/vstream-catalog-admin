package com.fugisawa.vstream.catalog

import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.CategoryRepository
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class MySQLCleanUpExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        val appContext = SpringExtension.getApplicationContext(context)
        listOf(
            appContext.getBean(CategoryRepository::class.java)
        ).forEach { it.deleteAll() }
    }
}