package com.fugisawa.vstream.catalog.admin.application.category.update

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertNotNull

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
            id == expectedId
                    && name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && createdAt == expected.createdAt
                    && updatedAt.isAfter(expected.updatedAt)
                    && deletedAt == null
        })
    }

    // propriedade invalida
    // atualizar para inativa
    // erro generico gateway
    // atualizar categoria com id inexistente


}
