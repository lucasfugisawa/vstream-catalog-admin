package br.com.fugisawa.vstream.catalog.admin.application.category.create

import br.com.fugisawa.vstream.catalog.admin.domain.category.Category
import br.com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class CreateCategoryUseCaseTest {

    @Test
    fun givenValidCommand_whenExecute_thenShouldCreateCategory() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = true

        val createCategoryCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedActive)

        val categoryGateway = mock<CategoryGateway>()

        whenever(categoryGateway.create(any())).thenAnswer { invocation ->
            invocation.getArgument<Category>(0)
        }

        val useCase = DefaultCreateCategoryUseCase(categoryGateway)

        val actualOutput: CreateCategoryOutput = useCase.execute(createCategoryCommand)

        verify(categoryGateway, times(1)).create(
            argThat { category ->
                category.name == expectedName
                        && category.description == expectedDescription
                        && category.active == expectedActive
                        && category.deletedAt == null
            }
        )
    }

    // TODO: Teste com propriedade inválida (name).
    // TODO: Teste criando categoria inativa.
    // TODO: Teste simulando um erro genérico vindo do gateway.
}
