package com.fugisawa.vstream.catalog.admin.application.category.update

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import com.fugisawa.vstream.catalog.admin.domain.exceptions.NotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue


class UpdateCategoryUseCaseTest {

    private lateinit var categoryGateway: CategoryGateway
    private lateinit var useCase: DefaultUpdateCategoryUseCase

    @BeforeEach
    fun beforeTest() {
        categoryGateway = mock<CategoryGateway>()
        useCase = DefaultUpdateCategoryUseCase(categoryGateway)
    }

    @Test
    fun `Given a valid command, when executing, then should update a category`() {
        val category = Category("Any", null, true)
        val expected = Category("Test", "This is a test", true)
        val expectedId = category.id

        val command = UpdateCategoryCommand(expectedId, expected.name, expected.description, expected.active)

        whenever(categoryGateway.findById(eq(expectedId))).thenReturn(category)
        whenever(categoryGateway.update(any())).thenAnswer { it.arguments.first() }

        val result = useCase.execute(command).rightOrNull()

        assertNotNull(result)
        verify(categoryGateway, times(1)).findById(eq(expectedId))
        verify(categoryGateway, times(1)).update(argThat {
            id.value == expectedId.value
                    && name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && createdAt == expected.createdAt
                    && deletedAt == null
        })
    }

    @Test
    fun `Given an invalid command with an invalid name, when executing, then should throw a domain exception`() {
        val category = Category("Any", "Any", true)
        val categoryId = category.id

        val command = UpdateCategoryCommand(categoryId, "", "This is a test", true)
        val expectedErrorMessage = "'name' must not be empty or blank"
        val expectedErrorCount = 2

        whenever(categoryGateway.findById(eq(categoryId))).thenReturn(category)

        val notification = useCase.execute(command).leftOrNull()
        assertEquals(expectedErrorCount, notification?.errors?.size)
        assertEquals(expectedErrorMessage, notification?.errors?.first()?.message)

        verify(categoryGateway, times(0)).create(any())
    }

    @Test
    fun `Given a valid inactivate command, when executing, then should inactivate a category`() {
        val category = Category("Test", "This is a test", true)
        val categoryId = category.id
        val command = UpdateCategoryCommand(categoryId, category.name, category.description, false)
        val expected = Category(category.name, category.description, false)

        assertTrue(category.active)
        assertNull(category.deletedAt)

        whenever(categoryGateway.findById(eq(categoryId))).thenReturn(category)
        whenever(categoryGateway.update(any())).thenAnswer { it.arguments.first() }

        val result = useCase.execute(command).rightOrNull()
        assertNotNull(result)
        verify(categoryGateway, times(1)).findById(eq(categoryId))
        verify(categoryGateway, times(1)).update(argThat {
            id.value == categoryId.value
                    && name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && createdAt == expected.createdAt
                    && deletedAt != null
        })
    }

    @Test
    fun `Given a valid command, when gateway error happens, then should throw IllegalStateException`() {
        val category = Category("Film", null, true)

        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida"
        val expectedIsActive = true
        val expectedId = category.id
        val expectedErrorCount = 1
        val expectedErrorMessage = "Gateway error"

        val aCommand = UpdateCategoryCommand(expectedId, expectedName, expectedDescription, expectedIsActive)

        whenever(categoryGateway.findById(eq(expectedId))).thenReturn(category)
        whenever(categoryGateway.update(any())).thenThrow(IllegalStateException(expectedErrorMessage))

        val notification = useCase.execute(aCommand).leftOrNull()

        Assertions.assertEquals(expectedErrorCount, notification?.errors?.size)
        Assertions.assertEquals(expectedErrorMessage, notification?.errors?.first()?.message)

        verify(categoryGateway, times(1)).update(argThat {
            id.value == expectedId.value
                    && name == expectedName
                    && description == expectedDescription
                    && active == expectedIsActive
                    && createdAt == category.createdAt
                    && deletedAt == null
        })
    }

    @Test
    fun `Given a command with an invalid Category ID, when execute, then should return Not Found Exception`() {
        val expectedCategory = Category("Test", "This is a test", true)
        val expectedId = CategoryID("123")
        val expectedErrorMessage = "Category with ID ${expectedId.value} not found"

        val command = UpdateCategoryCommand(expectedId, expectedCategory.name, expectedCategory.description, expectedCategory.active)

        whenever(categoryGateway.findById(eq(expectedId))).thenReturn(null)
        whenever(categoryGateway.update(any())).thenThrow(IllegalStateException(expectedErrorMessage))

        val actualException = assertThrows<NotFoundException> { useCase.execute(command) }

        Assertions.assertEquals(expectedErrorMessage, actualException.message)

        verify(categoryGateway, times(1)).findById(eq(expectedId))
        verify(categoryGateway, times(0)).update(any())
    }



}
