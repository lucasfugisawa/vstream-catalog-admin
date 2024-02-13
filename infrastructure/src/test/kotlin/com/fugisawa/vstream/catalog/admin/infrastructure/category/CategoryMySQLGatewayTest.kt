package com.fugisawa.vstream.catalog.admin.infrastructure.category

import com.fugisawa.vstream.catalog.MySQLGatewayTest
import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchQuery
import com.fugisawa.vstream.catalog.admin.domain.pagination.Pagination
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

        val updatedCategory =
            category.copy().update(name = expectedName, description = expectedDescription, active = expectedIsActive)

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


    @Test
    fun givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated() {
        val expectedPage = 0
        val expectedPerPage = 1
        val expectedTotal = 3

        val filmes = Category("Filmes", null, true)
        val series = Category("Séries", null, true)
        val documentarios = Category("Documentários", null, true)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAll(
            listOf(
                filmes.toJpaEntity(),
                series.toJpaEntity(),
                documentarios.toJpaEntity()
            )
        )

        assertEquals(3, categoryRepository.count())

        val query = CategorySearchQuery(0, 1, "", "name", CategorySearchQuery.Direction.ASC)
        val actualResult = categoryGateway.findAll(query)

        assertEquals(expectedPage, actualResult.current)
        assertEquals(expectedPerPage, actualResult.size)
        assertEquals(expectedTotal.toLong(), actualResult.total)
        assertEquals(expectedPerPage, actualResult.items.size)
        assertEquals(documentarios.id, actualResult.items.first().id)
    }

    @Test
    fun givenEmptyCategoriesTable_whenCallsFindAll_shouldReturnEmptyPage() {
        val expectedPage = 0
        val expectedPerPage = 1
        val expectedTotal = 0

        assertEquals(0, categoryRepository.count())

        val query = CategorySearchQuery(0, 1, "", "name", CategorySearchQuery.Direction.ASC)
        val actualResult = categoryGateway.findAll(query)

        assertEquals(expectedPage, actualResult.current)
        assertEquals(expectedPerPage, actualResult.size)
        assertEquals(expectedTotal.toLong(), actualResult.total)
        assertEquals(0, actualResult.items.size)
    }

    @Test
    fun givenFollowPagination_whenCallsFindAllWithPage1_shouldReturnPaginated() {
        var expectedPage = 0
        val expectedPerPage = 1
        val expectedTotal = 3

        val filmes = Category("Filmes", null, true)
        val series = Category("Séries", null, true)
        val documentarios = Category("Documentários", null, true)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAll(
            listOf(
                filmes.toJpaEntity(),
                series.toJpaEntity(),
                documentarios.toJpaEntity()
            )
        )

        assertEquals(3, categoryRepository.count())

        var query = CategorySearchQuery(0, 1, "", "name", CategorySearchQuery.Direction.ASC)
        var actualResult: Pagination<Category> = categoryGateway.findAll(query)

        assertEquals(expectedPage, actualResult.current)
        assertEquals(expectedPerPage, actualResult.size)
        assertEquals(expectedTotal.toLong(), actualResult.total)
        assertEquals(expectedPerPage, actualResult.items.size)
        assertEquals(documentarios.id, actualResult.items.first().id)

        // Page 1
        expectedPage = 1

        query = CategorySearchQuery(1, 1, "", "name", CategorySearchQuery.Direction.ASC)
        actualResult = categoryGateway.findAll(query)

        assertEquals(expectedPage, actualResult.current)
        assertEquals(expectedPerPage, actualResult.size)
        assertEquals(expectedTotal.toLong(), actualResult.total)
        assertEquals(expectedPerPage, actualResult.items.size)
        assertEquals(filmes.id, actualResult.items.first().id)

        // Page 2
        expectedPage = 2

        query = CategorySearchQuery(2, 1, "", "name", CategorySearchQuery.Direction.ASC)
        actualResult = categoryGateway.findAll(query)

        assertEquals(expectedPage, actualResult.current)
        assertEquals(expectedPerPage, actualResult.size)
        assertEquals(expectedTotal.toLong(), actualResult.total)
        assertEquals(expectedPerPage, actualResult.items.size)
        assertEquals(series.id, actualResult.items.first().id)
    }

    @Test
    fun givenPrePersistedCategoriesAndDocAsTerms_whenCallsFindAllAndTermsMatchsCategoryName_shouldReturnPaginated() {
        val expectedPage = 0
        val expectedPerPage = 1
        val expectedTotal = 1

        val filmes = Category("Filmes", null, true)
        val series = Category("Séries", null, true)
        val documentarios = Category("Documentários", null, true)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAll(
            listOf(
                filmes.toJpaEntity(),
                series.toJpaEntity(),
                documentarios.toJpaEntity()
            )
        )

        assertEquals(3, categoryRepository.count())

        val query = CategorySearchQuery(0, 1, "doc", "name", CategorySearchQuery.Direction.ASC)
        val actualResult = categoryGateway.findAll(query)

        assertEquals(expectedPage, actualResult.current)
        assertEquals(expectedPerPage, actualResult.size)
        assertEquals(expectedTotal.toLong(), actualResult.total)
        assertEquals(expectedPerPage, actualResult.items.size)
        assertEquals(documentarios.id, actualResult.items.first().id)
    }

    @Test
    fun givenPrePersistedCategoriesAndMaisAssistidaAsTerms_whenCallsFindAllAndTermsMatchsCategoryDescription_shouldReturnPaginated() {
        val expectedPage = 0
        val expectedPerPage = 1
        val expectedTotal = 1

        val filmes = Category("Filmes", "A categoria mais assistida", true)
        val series = Category("Séries", "Uma categoria assistida", true)
        val documentarios = Category("Documentários", "A categoria menos assistida", true)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAll(
            listOf(
                filmes.toJpaEntity(),
                series.toJpaEntity(),
                documentarios.toJpaEntity()
            )
        )

        assertEquals(3, categoryRepository.count())

        val query = CategorySearchQuery(0, 1, "MAIS ASSISTIDA", "name", CategorySearchQuery.Direction.ASC)
        val actualResult = categoryGateway.findAll(query)

        assertEquals(expectedPage, actualResult.current)
        assertEquals(expectedPerPage, actualResult.size)
        assertEquals(expectedTotal.toLong(), actualResult.total)
        assertEquals(expectedPerPage, actualResult.items.size)
        assertEquals(filmes.id, actualResult.items.first().id)
    }

    @Test
    fun givenPrePersistedCategories_whenCallsExistsByIds_shouldReturnIds() {
        // given
        val filmes = Category("Filmes", "A categoria mais assistida", true)
        val series = Category("Séries", "Uma categoria assistida", true)
        val documentarios = Category("Documentários", "A categoria menos assistida", true)

        assertEquals(0, categoryRepository.count())

        categoryRepository.saveAll(
            listOf(
                filmes.toJpaEntity(),
                series.toJpaEntity(),
                documentarios.toJpaEntity()
            )
        )

        assertEquals(3, categoryRepository.count())

        val expectedIds = listOf(filmes.id, series.id)

        val ids = listOf(filmes.id, series.id, CategoryID("123"))

        // when
        val actualResult = categoryGateway.existsByIds(ids)

        assertTrue(
            expectedIds.size == actualResult.size
                    && expectedIds.containsAll(actualResult)
        )
    }

}