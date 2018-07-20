package com.doublyapp.doubly

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EmailValidatorTest {

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun testValidEmailShouldReturnTrue() {
        assertTrue(EmailValidator.isValidEmail("testuser@gmail.com"))
    }

    @Test
    fun testEmptyEmailShouldReturnFalse() {
        assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun testShouldAcceptValidEmails() {
        val validEmails = arrayOf("user@example.com", "USER@foo.COM", "A_US-ER@foo.bar.org",
                "first.last@foo.jp alice+bob@baz.cn")
        for(email in validEmails) {
            assertTrue(EmailValidator.isValidEmail(email))
        }
    }

    @Test
    fun testShouldRejectInvalidEmails() {
        val invalidEmails = arrayOf("user@example,com", "user_at_foo.org", "user.name@example.",
                "foo@bar_baz.com foo@bar+baz.com")
        for(email in invalidEmails) {
            assertFalse(EmailValidator.isValidEmail(email))
        }
    }
}