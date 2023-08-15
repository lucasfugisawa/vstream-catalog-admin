package br.com.fugisawa.vstream.catalog.admin.domain.category

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun givenValidParams_onCallNewCategory_instantiateNewCategory() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = true

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        assertNotNull(actualCategory)
        assertNotNull(actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertNotNull(actualCategory.createdAt)
        assertNotNull(actualCategory.updatedAt)
        assertNotNull(actualCategory.deletedAt)
    }
}