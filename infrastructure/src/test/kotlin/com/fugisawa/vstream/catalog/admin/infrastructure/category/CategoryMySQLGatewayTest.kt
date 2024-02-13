package com.fugisawa.vstream.catalog.admin.infrastructure.category

import com.fugisawa.vstream.catalog.MySQLGatewayTest
import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.CategoryRepository
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.toJpaEntity
import org.junit.jupiter.api.Assertions.*
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

    @Test
    fun `Given a valid Category, when calling update, then should persist the updated category`() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val category = Category("Film", null, expectedIsActive)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAndFlush(category.toJpaEntity())

        assertEquals(1, categoryRepository.count())

        val actualInvalidEntity = categoryRepository.findById(category.id.value).getOrNull()

        assertEquals("Film", actualInvalidEntity?.name)
        assertNull(actualInvalidEntity?.description)
        assertEquals(expectedIsActive, actualInvalidEntity?.active)

        val updatedCategory = category.copy().update(name = expectedName, description = expectedDescription, active = expectedIsActive)

        val actualCategory = categoryGateway.update(updatedCategory)

        assertEquals(1, categoryRepository.count())

        assertEquals(category.id, actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedIsActive, actualCategory.active)
        assertEquals(category.createdAt, actualCategory.createdAt)
        assertTrue(category.updatedAt.isBefore(actualCategory.updatedAt))
        assertEquals(category.deletedAt, actualCategory.deletedAt)
        assertNull(actualCategory.deletedAt)

        val actualEntity = categoryRepository.findById(category.id.value).getOrNull()

        assertEquals(category.id.value, actualEntity?.id)
        assertEquals(expectedName, actualEntity?.name)
        assertEquals(expectedDescription, actualEntity?.description)
        assertEquals(expectedIsActive, actualEntity?.active)
        assertEquals(category.createdAt, actualEntity?.createdAt)
        assertTrue(category.updatedAt.isBefore(actualCategory.updatedAt))
        assertEquals(category.deletedAt, actualEntity?.deletedAt)
        assertNull(actualEntity?.deletedAt)
    }

    @Test
    fun `Given a pre-persisted category and valid Category id, when deleting it, then the category should be deleted`() {
        val category = Category("Filmes", null, true)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAndFlush(category.toJpaEntity())

        assertEquals(1, categoryRepository.count())

        categoryGateway.deleteById(category.id)

        assertEquals(0, categoryRepository.count())
    }

    @Test
    fun `Given an invalid category id, when deleting it, then the category should be deleted`() {
        assertEquals(0, categoryRepository.count())
        categoryGateway.deleteById(CategoryID("invalid"))
        assertEquals(0, categoryRepository.count())
    }

    @Test
    fun `Given a pre-persisted category and a valid category id, when calling findById, then the category should be retrieved`() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true

        val category = Category(expectedName, expectedDescription, expectedIsActive)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAndFlush(category.toJpaEntity())

        assertEquals(1, categoryRepository.count())

        val actualCategory = categoryGateway.findById(category.id)

        assertEquals(1, categoryRepository.count())

        assertEquals(category.id, actualCategory?.id)
        assertEquals(expectedName, actualCategory?.name)
        assertEquals(expectedDescription, actualCategory?.description)
        assertEquals(expectedIsActive, actualCategory?.active)
        assertEquals(category.createdAt, actualCategory?.createdAt)
        assertEquals(category.updatedAt, actualCategory?.updatedAt)
        assertEquals(category.deletedAt, actualCategory?.deletedAt)
        assertNull(actualCategory?.deletedAt)
    }

    @Test
    fun `Given a valid category id not stored yet, when calling findById, then it should return null category`() {
        assertEquals(0, categoryRepository.count())
        val actualCategory = categoryGateway.findById(CategoryID("empty"))
        assertNull(actualCategory)
    }

}