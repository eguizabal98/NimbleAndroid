package com.eem.data.repository

import com.eem.data.network.api.AuthApi
import com.eem.data.network.model.EmptyResponse
import com.eem.data.network.model.TestCoroutineContextProvider
import com.eem.data.network.model.auth.LoginAttributes
import com.eem.data.network.model.auth.LoginData
import com.eem.data.network.model.auth.LoginUserBody
import com.eem.data.network.model.auth.RegisterUserBody
import com.eem.data.network.model.auth.TokenResponse
import com.eem.data.network.model.auth.UserBody
import com.eem.data.room.dao.AccessTokenDao
import com.eem.data.room.model.AccessTokenEntity
import com.eem.domain.model.auth.LoginUser
import com.eem.domain.model.auth.RegisterUser
import com.eem.domain.model.base.Success
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Response

class AuthRepositoryImplTest {

    private lateinit var authRepository: AuthRepositoryImpl
    private val authApi = mock(AuthApi::class.java)
    private val accessTokenDao = mock(AccessTokenDao::class.java)
    private val contextProvider = TestCoroutineContextProvider()

    @Before
    fun setUp() {
        authRepository = AuthRepositoryImpl(authApi, accessTokenDao, contextProvider)
    }

    @Test
    fun `registerUser should call authApi-registerUser and clear accessTokenDao`(): Unit = runBlocking {
        val registerUser = RegisterUser("test@example.com", "John", "password", "password")
        `when`(authApi.registerUser(RegisterUserBody(UserBody(registerUser.email, registerUser.name, registerUser.password, registerUser.passwordConfirmation))))
            .thenReturn(Response.success(EmptyResponse()))

        val result = authRepository.registerUser(registerUser)

        // Assert
        verify(authApi).registerUser(RegisterUserBody(UserBody(registerUser.email, registerUser.name, registerUser.password, registerUser.passwordConfirmation)))
        verify(accessTokenDao).clear()
        assert(result is Success && result.data)
    }

    @Test
    fun `loginUser should call authApi-loginUser and insert AccessTokenEntity into accessTokenDao`() = runBlocking {
        val loginUser = LoginUser("test@example.com", "password")
        val accessTokenResponse = TokenResponse(LoginData("id","Bearer", LoginAttributes("a1221","Barer",12,"a45121",12)))
        `when`(authApi.loginUser(LoginUserBody(email = loginUser.email, password =loginUser.password))).thenReturn(Response.success(accessTokenResponse))

        val result = authRepository.loginUser(loginUser)

        // Assert
        verify(authApi).loginUser(LoginUserBody(email = loginUser.email, password = loginUser.password))
        verify(accessTokenDao).insertAll(AccessTokenEntity("a1221","Barer",12,"a45121",12))
        assert(result is Success && result.data)
    }
}