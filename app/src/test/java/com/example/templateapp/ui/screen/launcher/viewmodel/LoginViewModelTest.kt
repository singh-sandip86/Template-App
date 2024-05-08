package com.example.templateapp.ui.screen.launcher.viewmodel

import app.cash.turbine.test
import com.example.templateapp.R
import com.example.templateapp.dto.response.login.LoginResponse
import com.example.templateapp.model.login.LoginError
import com.example.templateapp.ui.screen.launcher.repository.LoginRepository
import com.example.templateapp.util.MainDispatcherRule
import com.example.templateapp.util.convertJsonToModel
import com.example.templateapp.util.loadJsonAsString
import com.example.templateapp.utils.ErrorUtils
import com.example.templateapp.utils.ResourceProvider
import com.example.templateapp.utils.empty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    companion object {
        private const val LOGIN_RESPONSE = "login/login_data.json"

        const val LOGIN_EMAIL = "nikhil+agent3@torinit.com"
        const val LOGIN_PASSWORD = "kF!@367SApyd"
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var loginRepository: LoginRepository

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    @Mock
    private lateinit var errorUtils: ErrorUtils

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginViewModel = LoginViewModel(loginRepository, resourceProvider, errorUtils)

        Mockito.`when`(resourceProvider.getString(R.string.error_email_id_required))
            .thenReturn("Email id is required")
        Mockito.`when`(resourceProvider.getString(R.string.error_invalid_email_address))
            .thenReturn("Invalid Email Address")
        Mockito.`when`(resourceProvider.getString(R.string.error_password_required))
            .thenReturn("Password is required")
        Mockito.`when`(resourceProvider.getString(R.string.error_invalid_password))
            .thenReturn("Invalid Password")
    }

    @Test
    fun onEmailChange() {
        runTest {
            loginViewModel.onEmailChange("Email")
            assertEquals(false, loginViewModel.validateAndSignIn.first())
        }
        assertEquals("Email", loginViewModel.emailFlow.value)
    }

    @Test
    fun onPasswordChange() {
        runTest {
            loginViewModel.onPasswordChange("Password")
            assertEquals(false, loginViewModel.validateAndSignIn.first())
        }
        assertEquals("Password", loginViewModel.passwordFlow.value)
    }

    @Test
    fun `onLoginClick validateAndSignIn return true`() {
        runTest {
            loginViewModel.onLoginClick()
            assertEquals(true, loginViewModel.validateAndSignIn.first())
        }
    }

    @Test
    fun `validate false`() {
        val value = loginViewModel.validate("Email", "Password", false)
        assertEquals(LoginError(), value)
    }

    @Test
    fun `validate true with empty email`() {
        val value = loginViewModel.validate(String.empty(), "Password", true)
        assertEquals(
            LoginError(
                emailInvalid = true,
                errorEmail = "Email id is required",
                passwordInvalid = false,
                errorPassword = String.empty()
            ), value
        )
    }

    @Test
    fun `validate true with invalid email`() {
        val value = loginViewModel.validate("Email", "Password", true)
        assertEquals(
            LoginError(
                emailInvalid = true,
                errorEmail = "Invalid Email Address",
                passwordInvalid = false,
                errorPassword = String.empty()
            ), value
        )
    }

    @Test
    fun `validate true with empty password`() {
        val value = loginViewModel.validate("sandip@torinit.com", String.empty(), true)
        assertEquals(
            LoginError(
                emailInvalid = false,
                errorEmail = String.empty(),
                passwordInvalid = true,
                errorPassword = "Password is required"
            ), value
        )
    }

    @Test
    fun `validate true with invalid password`() {
        val value = loginViewModel.validate("sandip@torinit.com", "Pass", true)
        assertEquals(
            LoginError(
                emailInvalid = false,
                errorEmail = String.empty(),
                passwordInvalid = true,
                errorPassword = "Invalid Password"
            ), value
        )
    }

    @Test
    fun `validate true with empty email and empty password`() {
        val value = loginViewModel.validate(String.empty(), String.empty(), true)
        assertEquals(
            LoginError(
                emailInvalid = true,
                errorEmail = "Email id is required",
                passwordInvalid = true,
                errorPassword = "Password is required"
            ), value
        )
    }

    @Test
    fun `validate true with empty email and invalid password`() {
        val value = loginViewModel.validate(String.empty(), "Pass", true)
        assertEquals(
            LoginError(
                emailInvalid = true,
                errorEmail = "Email id is required",
                passwordInvalid = true,
                errorPassword = "Invalid Password"
            ), value
        )
    }

    @Test
    fun `validate true with invalid email and empty password`() {
        val value = loginViewModel.validate("Email", String.empty(), true)
        assertEquals(
            LoginError(
                emailInvalid = true,
                errorEmail = "Invalid Email Address",
                passwordInvalid = true,
                errorPassword = "Password is required"
            ), value
        )
    }

    @Test
    fun `validate true with invalid email and invalid password`() {
        val value = loginViewModel.validate("Email", "Pass", true)
        assertEquals(
            LoginError(
                emailInvalid = true,
                errorEmail = "Invalid Email Address",
                passwordInvalid = true,
                errorPassword = "Invalid Password"
            ), value
        )
    }

    @Test
    fun `validate true with valid email and valid password`() {
        val value = loginViewModel.validate("sandip@torinit.com", "Password", true)
        assertEquals(
            LoginError(
                emailInvalid = false,
                errorEmail = String.empty(),
                passwordInvalid = false,
                errorPassword = String.empty()
            ), value
        )
    }

    @Test
    fun `verify login success`() = runTest {
        val jsonString = loadJsonAsString(javaClass, LOGIN_RESPONSE)
        val model = convertJsonToModel(jsonString, LoginResponse::class.java)
        val response = Response.success(model)

        Mockito.`when`(loginRepository.loginUser(email = LOGIN_EMAIL, password = LOGIN_PASSWORD))
            .thenReturn(response)

        loginViewModel.onEmailChange(LOGIN_EMAIL)
        loginViewModel.onPasswordChange(LOGIN_PASSWORD)
        loginViewModel.logIn()

        loginViewModel.user.test {
            val item = awaitItem()
            assertEquals(model.data?.userId, item.getOrNull()?.userId)
        }
    }

    @Test
    fun `verify login fail`() = runTest {
        val response = Response.success(LoginResponse().apply { data = null })

        Mockito.`when`(loginRepository.loginUser(email = LOGIN_EMAIL, password = LOGIN_PASSWORD))
            .thenReturn(response)

        loginViewModel.onEmailChange(LOGIN_EMAIL)
        loginViewModel.onPasswordChange(LOGIN_PASSWORD)
        loginViewModel.logIn()

        loginViewModel.user.test {
            val item = expectNoEvents()
            assertTrue(item != null)
            cancelAndIgnoreRemainingEvents()
        }
    }
}