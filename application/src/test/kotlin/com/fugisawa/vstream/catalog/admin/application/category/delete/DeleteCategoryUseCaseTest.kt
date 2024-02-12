package com.fugisawa.vstream.catalog.admin.application.category.delete

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import kotlin.test.assertEquals


class DeleteCategoryUseCaseTest {

    private lateinit var categoryGateway: CategoryGateway
    private lateinit var useCase: DefaultDeleteCategoryUseCase

    @BeforeEach
    fun beforeTest() {
        categoryGateway = mock<CategoryGateway>()
        useCase = DefaultDeleteCategoryUseCase(categoryGateway)
    }

    @Test
    fun `Given a command with a valid Category ID, when executing, should be OK`() {
        val category = Category("Any", "Any", true)
        val expectedId = category.id

        doNothing().whenever(categoryGateway).deleteById(eq(expectedId))

        assertDoesNotThrow { useCase.execute(expectedId) }
        verify(categoryGateway, times(1)).deleteById(eq(expectedId))
    }

    fun `Given a command with an invalid Category ID, when executing, should be OK`() {
        val expectedId = CategoryID("123")

        doNothing().whenever(categoryGateway).deleteById(eq(expectedId))

        assertDoesNotThrow { useCase.execute(expectedId) }
        verify(categoryGateway, times(1)).deleteById(eq(expectedId))
    }

    fun `Given a valid command, when gateway error happens, then should throw IllegalStateException`() {
        val category = Category("Any", "Any", true)
        val expectedId = category.id
        val expectedMessage = "Gateway error"

        doThrow(IllegalStateException(expectedMessage)).whenever(categoryGateway).deleteById(eq(expectedId))

        val assertThrows = assertThrows<IllegalStateException> { useCase.execute(expectedId) }
        assertEquals(assertThrows.message, expectedMessage)
        verify(categoryGateway, times(1)).deleteById(eq(expectedId))
    }

}
