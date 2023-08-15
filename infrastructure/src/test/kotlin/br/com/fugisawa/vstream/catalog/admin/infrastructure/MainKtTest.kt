package br.com.fugisawa.vstream.catalog.admin.infrastructure

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MainKtTest {

    @Test
    fun testMain() {
        Assertions.assertNotNull(main(arrayOf("test")))
    }
}