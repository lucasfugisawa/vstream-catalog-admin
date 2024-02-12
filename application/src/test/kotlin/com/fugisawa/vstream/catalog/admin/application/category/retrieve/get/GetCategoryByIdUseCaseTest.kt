package com.fugisawa.vstream.catalog.admin.application.category.retrieve.get

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import com.fugisawa.vstream.catalog.admin.domain.exceptions.NotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class GetCategoryByIdUseCaseTest {
    private lateinit var categoryGateway: CategoryGateway
    private lateinit var useCase: DefaultGetCategoryByIdUseCase

    @BeforeEach
    fun beforeTest() {
        categoryGateway = mock<CategoryGateway>()
        useCase = DefaultGetCategoryByIdUseCase(categoryGateway)
    }

    @Test
    fun `Given a valid Category ID, when executing, then should return the Category`() {
        val category = Category("Any", "Any", true)
        val expectedCategoryId = category.id
        val expectedCategory = Category("Any", "Any", true)

        whenever(categoryGateway.findById(eq(expectedCategoryId))).thenReturn(expectedCategory)

        val actualCategory = useCase.execute(expectedCategoryId)

        assertEquals(actualCategory.id, expectedCategory.id)
        assertEquals(actualCategory.name, expectedCategory.name)
        assertEquals(actualCategory.description, expectedCategory.description)
        assertEquals(actualCategory.active, expectedCategory.active)
        assertEquals(actualCategory.createdAt, expectedCategory.createdAt)
        assertEquals(actualCategory.updatedAt, expectedCategory.updatedAt)
        assertEquals(actualCategory.deletedAt, expectedCategory.deletedAt)
    }

    @Test
    fun `Given an invalid Category ID, when executing, then should return Not Found Exception`() {
        val categoryId = CategoryID("123")
        val expectedErrorMessage = "Category with ID ${categoryId.value} not found"

        whenever(categoryGateway.findById(eq(categoryId))).thenReturn(null)

        val actualException = assertThrows<NotFoundException> { useCase.execute(categoryId) }

        assertEquals(expectedErrorMessage, actualException.message)
    }

    @Test
    fun `Given a valid ID, when gateway error happens, then should throw IllegalStateException`() {
        val categoryId = CategoryID("123")
        val expectedErrorMessage = "Gateway error"

        whenever(categoryGateway.findById(eq(categoryId))).thenThrow(IllegalStateException(expectedErrorMessage))

        val actualException = assertThrows<IllegalStateException> { useCase.execute(categoryId) }

        assertEquals(expectedErrorMessage, actualException.message)
    }
}