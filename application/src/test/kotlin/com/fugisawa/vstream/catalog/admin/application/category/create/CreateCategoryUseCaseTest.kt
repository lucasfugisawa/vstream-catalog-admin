import com.fugisawa.vstream.catalog.admin.application.category.create.CreateCategoryCommand
import com.fugisawa.vstream.catalog.admin.application.category.create.DefaultCreateCategoryUseCase
import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
        val command = CreateCategoryCommand.with("Test", "This is a test", true)
        val expected = Category("Test", "This is a test", true)

        whenever(categoryGateway.create(any())).thenAnswer { it.arguments.first() }

        val result = useCase.execute(command).rightOrNull()
        assertNotNull(result)

        verify(categoryGateway, times(1)).create(argThat {
            name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && deletedAt == null
        })
    }

    @Test
    fun `Given an invalid command with an invalid name, when executing, then should throw a domain exception`() {
        val command = CreateCategoryCommand.with("", "This is a test", true)
        val expectedErrorMessage = "'name' must not be empty or blank"
        val expectedErrorCount = 2

        val notification = useCase.execute(command).leftOrNull()
        assertEquals(expectedErrorCount, notification?.errors?.size)
        assertEquals(expectedErrorMessage, notification?.errors?.first()?.message)

        verify(categoryGateway, times(0)).create(any())
    }

    @Test
    fun `Given a valid command and inactive initial value, when executing, then should create a category`() {
        val command = CreateCategoryCommand.with("Test", "This is a test", false)
        val expected = Category("Test", "This is a test", false)

        whenever(categoryGateway.create(any())).thenAnswer { it.arguments.first() }

        val result = useCase.execute(command).rightOrNull()
        assertNotNull(result)

        verify(categoryGateway, times(1)).create(argThat {
            name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && deletedAt != null
        })
    }

    @Test
    fun `Given a valid command, when gateway error happens, then should throw IllegalStateException`() {
        val command = CreateCategoryCommand.with("Test", "This is a test", true)
        val expected = Category("Test", "This is a test", true)
        val expectedErrorMessage = "Gateway error"
        val expectedErrorCount = 1

        whenever(categoryGateway.create(any())).thenThrow(IllegalStateException(expectedErrorMessage))

        val notification = useCase.execute(command).leftOrNull()
        assertEquals(expectedErrorCount, notification?.errors?.size)
        assertEquals(expectedErrorMessage, notification?.errors?.first()?.message)

        verify(categoryGateway, times(1)).create(argThat {
            name == expected.name
                    && description == expected.description
                    && active == expected.active
                    && deletedAt == null
        })
    }
}