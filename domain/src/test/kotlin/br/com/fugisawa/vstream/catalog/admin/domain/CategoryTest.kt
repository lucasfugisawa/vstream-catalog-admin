package br.com.fugisawa.vstream.catalog.admin.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun testNewCategory() {
        Assertions.assertNotNull(Category())
    }
}