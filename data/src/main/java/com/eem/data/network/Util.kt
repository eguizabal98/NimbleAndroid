package com.eem.data.network

import com.eem.data.BuildConfig
import com.eem.data.network.api.AuthApi
import com.eem.data.network.model.NullOnEmptyConverterFactory
import com.eem.data.network.model.auth.RefreshTokenBody
import com.eem.data.room.dao.AccessTokenDao
import com.eem.data.room.model.AccessTokenEntity
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

fun <Api> buildApi(
    interceptors: List<Interceptor> = emptyList(),
    authenticator: Authenticator? = null,
    api: Class<Api>
): Api {
    val client = OkHttpClient.Builder()
    interceptors.forEach {
        client.addInterceptor(it)
    }
    authenticator?.let { client.authenticator(it) }
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client.build())
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(api)
}

fun getLoggerInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

class AuthenticationNimble @Inject constructor(
    private val accessTokenDao: AccessTokenDao,
    private val authApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request =
        runBlocking {
            var token = ""
            val requestBuilder = response.request.newBuilder()
            val responseToken = authApi.refreshToken(
                RefreshTokenBody(
                    refreshToken = accessTokenDao.getAll()?.first()?.refreshToken.orEmpty()
                )
            )
            if (responseToken.isSuccessful) {
                responseToken.body()?.let {
                    accessTokenDao.clear()
                    token = "${it.data.attributes.tokenType} ${it.data.attributes.accessToken}"
                    accessTokenDao.insertAll(
                        AccessTokenEntity(
                            it.data.attributes.accessToken,
                            it.data.attributes.tokenType,
                            it.data.attributes.expiresIn,
                            it.data.attributes.refreshToken,
                            it.data.attributes.createdAt
                        )
                    )
                }
            }
            requestBuilder.header("Authorization", token)
            //Try this request with the new header
            requestBuilder.build()
        }


}