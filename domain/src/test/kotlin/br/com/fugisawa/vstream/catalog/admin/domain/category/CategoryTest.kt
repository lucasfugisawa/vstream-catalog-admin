package br.com.fugisawa.vstream.catalog.admin.domain.category

import br.com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException
import br.com.fugisawa.vstream.catalog.admin.domain.validation.handler.DomainExceptionValidationHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun givenValidParams_onCallNewCategory_instantiateNewCategory() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = true

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        assertNotNull(actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertNotNull(actualCategory.createdAt)
        assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenInvalidEmptyName_onCallNewCategoryAndValidate_receiveError() {
        val expectedName = ""
        val expectedDescription = "A categoria mais assistida!"
        val expectedErrorCount = 1
        val expectedActive = true
        val expectedErrorMessage = "'name' must not be empty or blank"

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        val actualException =
            assertThrows(DomainException::class.java) { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedErrorMessage, actualException.errors.first().message)
        assertEquals(expectedErrorCount, actualException.errors.size)
    }

    @Test
    fun givenInvalidBlankName_onCallNewCategoryAndValidate_receiveError() {
        val expectedName = "   "
        val expectedDescription = "A categoria mais assistida!"
        val expectedErrorCount = 1
        val expectedActive = true
        val expectedErrorMessage = "'name' must not be empty or blank"

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        val actualException =
            assertThrows(DomainException::class.java) { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedErrorMessage, actualException.errors.first().message)
        assertEquals(expectedErrorCount, actualException.errors.size)
    }

    @Test
    fun givenInvalidNameLengthLessThan3Chars_onCallNewCategoryAndValidate_receiveError() {
        val expectedName = "ab "
        val expectedDescription = "A categoria mais assistida!"
        val expectedErrorCount = 1
        val expectedActive = true
        val expectedErrorMessage = "'name' must be at between 3 and 255 characters long"

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        val actualException =
            assertThrows(DomainException::class.java) { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedErrorMessage, actualException.errors.first().message)
        assertEquals(expectedErrorCount, actualException.errors.size)
    }

    @Test
    fun givenInvalidNameLengthMoreThan255Chars_onCallNewCategoryAndValidate_receiveError() {
        val expectedName: String = "a".repeat(256)
        val expectedDescription = "A categoria mais assistida!"
        val expectedErrorCount = 1
        val expectedActive = true
        val expectedErrorMessage = "'name' must be at between 3 and 255 characters long"

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        val actualException =
            assertThrows(DomainException::class.java) { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedErrorMessage, actualException.errors.first().message)
        assertEquals(expectedErrorCount, actualException.errors.size)
    }

    @Test
    fun givenValidEmptyDescription_onCallNewCategoryAndValidate_instantiateNewCategory() {
        val expectedName = "Filmes"
        val expectedDescription = "  "
        val expectedActive = true

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertNotNull(actualCategory.createdAt)
        assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenValidNullDescription_onCallNewCategoryAndValidate_instantiateNewCategory() {
        val expectedName = "Filmes"
        val expectedActive = true

        val actualCategory = Category.newCategory(expectedName, null, expectedActive)

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedName, actualCategory.name)
        assertEquals(null, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertNotNull(actualCategory.createdAt)
        assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenValidFalseActive_onCallNewCategoryAndValidate_instantiateNewCategory() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = false

        val actualCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertNotNull(actualCategory.createdAt)
        assertNotNull(actualCategory.deletedAt)
    }

    @Test
    fun givenValidActiveCategory_onCallDeactivate_categoryMustBeDeactivated() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = true

        val aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

        // val updatedAt = aCategory.updatedAt
        val createdAt = aCategory.createdAt

        assertTrue(aCategory.active)
        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }
        assertNull(aCategory.deletedAt)
        assertNotNull(aCategory.createdAt)

        val actualCategory = aCategory.deactivate()

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertFalse(actualCategory.active)
        assertEquals(createdAt, actualCategory.createdAt)
        assertNotNull(actualCategory.deletedAt)
        // assertTrue(actualCategory.deletedAt!!.isAfter(updatedAt))
        // assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
    }

    @Test
    fun givenValidInactiveCategory_onCallActivate_categoryMustBeActivated() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = false

        val aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

//        val updatedAt = aCategory.updatedAt
        val createdAt = aCategory.createdAt

        assertFalse(aCategory.active)
        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }
        assertNotNull(aCategory.deletedAt)
        assertNotNull(aCategory.createdAt)

        val actualCategory = aCategory.activate()

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertTrue(actualCategory.active)
        assertEquals(createdAt, actualCategory.createdAt)
        assertNull(actualCategory.deletedAt)
        // assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
    }

    @Test
    fun givenValidCategory_onCallUpdate_updateCategory() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = false

        val aCategory =
            Category.newCategory("Filmes with typo", "A categoria mais assistida with typo!", true)

        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }
        assertNull(aCategory.deletedAt)
        assertTrue(aCategory.active)

//        val updatedAt = aCategory.updatedAt
        val createdAt = aCategory.createdAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive)

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(aCategory.id, actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        // assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
        assertEquals(createdAt, actualCategory.createdAt)
        assertNull(actualCategory.deletedAt)
    }

    @Test
    fun givenValidCategory_onCallUpdateToInactive_updateAndInactivateCategory() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = false

        val aCategory =
            Category.newCategory("Filmes with typo", "A categoria mais assistida with typo!", true)

        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }

//        val updatedAt = aCategory.updatedAt
        val createdAt = aCategory.createdAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive)

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(aCategory.id, actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        // assertTrue(actualCategory.updatedAt.isAfter(updatedAt))
        assertEquals(createdAt, actualCategory.createdAt)
        assertNotNull(actualCategory.deletedAt)
        // assertTrue(actualCategory.deletedAt!!.isAfter(updatedAt))
    }

    @Test
    fun givenValidCategory_onCallUpdateWithInvalidArguments_updateCategory() {
        val expectedName = "F"
        val expectedDescription = "A"
        val expectedActive = false

        val aCategory =
            Category.newCategory("Filmes with typo", "A categoria mais assistida with typo!", true)

        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }

//        val updatedAt = aCategory.updatedAt
        val createdAt = aCategory.createdAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive)

        assertEquals(aCategory.id, actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertEquals(createdAt, actualCategory.createdAt)
        assertNotNull(actualCategory.deletedAt)
        // assertTrue(actualCategory.deletedAt!!.isAfter(updatedAt))
    }
}