import br.com.fugisawa.vstream.catalog.admin.application.category.create.CreateCategoryCommand
import br.com.fugisawa.vstream.catalog.admin.application.category.create.DefaultCreateCategoryUseCase
import br.com.fugisawa.vstream.catalog.admin.domain.category.Category
import br.com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import br.com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import kotlin.test.assertEquals

class DefaultCreateCategoryUseCaseTest {
    private lateinit var categoryGateway: CategoryGateway
    private lateinit var useCase: DefaultCreateCategoryUseCase

    @BeforeEach
    fun beforeTest() {
        categoryGateway = mock<CategoryGateway>()
        useCase = DefaultCreateCategoryUseCase(categoryGateway)
    }

    @Test
    fun `Given a valid command, when executing, then should create a category`() {
        val command = CreateCategoryCommand("Test", "This is a test", true)
        val expected = Category.newCategory("Test", "This is a test", true)

        whenever(categoryGateway.create(any())).thenAnswer { it.arguments[0] }

        val result = useCase.execute(command)

        verify(categoryGateway, times(1)).create(argThat {
            name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && deletedAt == null
        })
    }

    @Test
    fun `Given an invalid command with an invalid name, when executing, then should throw a domain exception`() {
        val command = CreateCategoryCommand("   ", "This is a test", false)
        val expectedErrorMessage = "'name' must not be empty or blank"

        val actualException = assertThrows<DomainException>{ useCase.execute(command) }

        assertEquals(expectedErrorMessage, actualException.errors[0].message)
        verify(categoryGateway, times(0)).create(any())
    }

    @Test
    fun `Given a valid command and inactive initial value, when executing, then should create a category`() {
        val command = CreateCategoryCommand("Test", "This is a test", false)
        val expected = Category.newCategory("Test", "This is a test", false)

        whenever(categoryGateway.create(any())).thenAnswer { it.arguments[0] }

        val result = useCase.execute(command)
        verify(categoryGateway, times(1)).create(argThat {
            name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && deletedAt != null
        })
    }

    @Test
    fun `Given a valid command, when gateway error happens, then should throw IllegalStateException`() {
        val command = CreateCategoryCommand("Test", "This is a test", true)
        val expected = Category.newCategory("Test", "This is a test", true)

        val expectedErrorMessage = "Gateway error"

        whenever(categoryGateway.create(any())).thenThrow(IllegalStateException(expectedErrorMessage))

        val actualException = assertThrows<IllegalStateException> { useCase.execute(command) }

        verify(categoryGateway, times(1)).create(argThat {
            name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && deletedAt == null
        })
    }
}