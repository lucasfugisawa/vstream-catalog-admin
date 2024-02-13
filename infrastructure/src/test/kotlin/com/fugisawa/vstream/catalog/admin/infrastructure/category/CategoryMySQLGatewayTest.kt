package com.fugisawa.vstream.catalog.admin.infrastructure.category

import com.fugisawa.vstream.catalog.MySQLGatewayTest
import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.CategoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.jvm.optionals.getOrNull

@MySQLGatewayTest
class CategoryMySQLGatewayTest @Autowired constructor(
    val categoryGateway: CategoryMySQLGateway,
    val categoryRepository: CategoryRepository,
) {
    @Test
    fun `Given a valid Category, when calling create, then should persist a the new category`() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val category = Category(expectedName, expectedDescription, expectedIsActive)

        assertEquals(0, categoryRepository.count())

        val actualCategory = categoryGateway.create(category)

        assertEquals(1, categoryRepository.count())

        assertEquals(category.id, actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedIsActive, actualCategory.active)
        assertEquals(category.createdAt, actualCategory.createdAt)
        assertEquals(category.updatedAt, actualCategory.updatedAt)
        assertEquals(category.deletedAt, actualCategory.deletedAt)
        assertNull(actualCategory.deletedAt)

        val actualEntity = categoryRepository.findById(category.id.value).getOrNull()

        assertEquals(category.id.value, actualEntity?.id)
        assertEquals(expectedName, actualEntity?.name)
        assertEquals(expectedDescription, actualEntity?.description)
        assertEquals(expectedIsActive, actualEntity?.active)
        assertEquals(category.createdAt, actualEntity?.createdAt)
        assertEquals(category.updatedAt, actualEntity?.updatedAt)
        assertEquals(category.deletedAt, actualEntity?.deletedAt)
        assertNull(actualEntity?.deletedAt)
    }
}