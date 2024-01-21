package com.fugisawa.vstream.catalog.admin.domain.category

import com.fugisawa.vstream.catalog.admin.domain.exceptions.DomainException
import com.fugisawa.vstream.catalog.admin.domain.validation.handler.DomainExceptionValidationHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun `given valid parameters, when calling new Category, then should instantiate a new Category`() {
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
    fun `given an invalid empty name, when calling new Category and validate, then should receive an error`() {
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
    fun `given an invalid blank name, when calling new Category, then should receive an error`() {
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
    fun `given an invalid name with length less than 3 characters, when calling new Category and validate, then should receive an error`() {
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
    fun `given an invalid name with length more than 255 characters, when calling new Category and validate, then should receive an error`() {
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
    fun `given a valid empty description, when calling new Category and validate, then should instantiate new Category`() {
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
    fun `given a valid null description, when calling new Category and validate, then should instantiate new Category`() {
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
    fun `given a valid false active argument, when calling new Category and validate, then should instantiate new Category`() {
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
    fun `given a valid true active argument, when calling deactivate, then category should be deactivated`() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = true

        val aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

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
    }

    @Test
    fun `given a valid inactive Category, when calling activate, then category should be activated`() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = false

        val aCategory = Category.newCategory(expectedName, expectedDescription, expectedActive)

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
    }

    @Test
    fun `given a valid Category, when calling update, then should update Category`() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = false

        val aCategory =
            Category.newCategory("Filmes with typo", "A categoria mais assistida with typo!", true)

        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }
        assertNull(aCategory.deletedAt)
        assertTrue(aCategory.active)

        val createdAt = aCategory.createdAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive)

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(aCategory.id, actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertEquals(createdAt, actualCategory.createdAt)
        assertNotNull(actualCategory.deletedAt)
    }

    @Test
    fun `given a valid Category, when calling update to inactive, then should update and inactivate Category`() {
        val expectedName = "Filmes"
        val expectedDescription = "A categoria mais assistida!"
        val expectedActive = false

        val aCategory =
            Category.newCategory("Filmes with typo", "A categoria mais assistida with typo!", true)

        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }

        val createdAt = aCategory.createdAt

        val actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive)

        assertDoesNotThrow { actualCategory.validate(DomainExceptionValidationHandler()) }
        assertEquals(aCategory.id, actualCategory.id)
        assertEquals(expectedName, actualCategory.name)
        assertEquals(expectedDescription, actualCategory.description)
        assertEquals(expectedActive, actualCategory.active)
        assertEquals(createdAt, actualCategory.createdAt)
        assertNotNull(actualCategory.deletedAt)
    }

    @Test
    fun `given a valid Category, when calling update with invalid arguments without validating, then should update category`() {
        val expectedName = "F"
        val expectedDescription = "A"
        val expectedActive = false

        val aCategory =
            Category.newCategory("Filmes with typo", "A categoria mais assistida with typo!", true)

        assertDoesNotThrow { aCategory.validate(DomainExceptionValidationHandler()) }

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