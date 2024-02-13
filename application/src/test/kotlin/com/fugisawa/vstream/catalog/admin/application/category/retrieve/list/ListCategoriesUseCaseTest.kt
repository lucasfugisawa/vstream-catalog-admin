package com.fugisawa.vstream.catalog.admin.application.category.retrieve.list

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchQuery
import com.fugisawa.vstream.catalog.admin.domain.pagination.Pagination
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class ListCategoriesUseCaseTest {
    private lateinit var categoryGateway: CategoryGateway
    private lateinit var useCase: DefaultListCategoriesUseCase

    @BeforeEach
    fun beforeTest() {
        categoryGateway = mock<CategoryGateway>()
        useCase = DefaultListCategoriesUseCase(categoryGateway)
    }

    @Test
    fun `Given a valid query, when executing, then should return the categories list`() {
        val categories = listOf(
            Category("Filmes", null, true),
            Category("Series", null, true),
        )

        val expectedPage = 0
        val expectedPerPage = 10
        val expectedTerms = ""
        val expectedSort = "createdAt"
        val expectedDirection = CategorySearchQuery.Direction.ASC

        val query =
            CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection)

        val expectedPaginationResult = Pagination(expectedPage, expectedPerPage, categories.size.toLong(), categories)

        val expectedItemsCount = 2
        val expectedResult = expectedPaginationResult.run {
            Pagination(
                current = this.current,
                size = this.size,
                total = this.total,
                items = this.items.map(::categoryToCategoryListOutput)
            )
        }

        whenever(categoryGateway.findAll(eq(query))).thenReturn(expectedPaginationResult)

        val actualResult = useCase.execute(query)

        Assertions.assertEquals(expectedItemsCount, actualResult.items.size)
        Assertions.assertEquals(expectedResult, actualResult)
        Assertions.assertEquals(expectedPage, actualResult.current)
        Assertions.assertEquals(expectedPerPage, actualResult.size)
        Assertions.assertEquals(categories.size.toLong(), actualResult.total)
    }

    @Test
    fun `Given a valid query, when executing but no results, then should return empty categories list`() {
        val categories = listOf<Category>()

        val expectedPage = 0
        val expectedPerPage = 10
        val expectedTerms = ""
        val expectedSort = "createdAt"
        val expectedDirection = CategorySearchQuery.Direction.ASC

        val query =
            CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection)

        val expectedPaginationResult = Pagination(expectedPage, expectedPerPage, categories.size.toLong(), categories)

        val expectedItemsCount = 0
        val expectedResult = expectedPaginationResult.run {
            Pagination(
                current = this.current,
                size = this.size,
                total = this.total,
                items = this.items.map(::categoryToCategoryListOutput)
            )
        }

        whenever(categoryGateway.findAll(eq(query))).thenReturn(expectedPaginationResult)

        val actualResult = useCase.execute(query)

        Assertions.assertEquals(expectedItemsCount, actualResult.items.size)
        Assertions.assertEquals(expectedResult, actualResult)
        Assertions.assertEquals(expectedPage, actualResult.current)
        Assertions.assertEquals(expectedPerPage, actualResult.size)
        Assertions.assertEquals(categories.size.toLong(), actualResult.total)
    }

    @Test
    fun `Given a valid query, when gateway error happens, then should throw IllegalStateException`() {
        val expectedPage = 0
        val expectedPerPage = 10
        val expectedTerms = ""
        val expectedSort = "createdAt"
        val expectedDirection = CategorySearchQuery.Direction.ASC
        val expectedErrorMessage = "Gateway error"

        val query =
            CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection)

        whenever(categoryGateway.findAll(eq(query))).thenThrow(IllegalStateException(expectedErrorMessage))

        val actualException = assertThrows<IllegalStateException> { useCase.execute(query) }

        Assertions.assertEquals(expectedErrorMessage, actualException.message)
    }


}