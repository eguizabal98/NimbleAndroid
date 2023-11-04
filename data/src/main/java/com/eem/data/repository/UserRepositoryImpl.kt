package com.eem.data.repository

import com.eem.data.network.api.UserApi
import com.eem.data.network.model.CoroutineContextProvider
import com.eem.data.room.dao.AccessTokenDao
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.user.ProfileInfo
import com.eem.domain.model.user.UserAttributesInfo
import com.eem.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val accessTokenDao: AccessTokenDao,
    private val contextProvider: CoroutineContextProvider
) : UserRepository {

    override suspend fun getProfileInfo(): ResultWrapper<ProfileInfo> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                userApi.getProfileInfo(accessTokenDao.getAll()?.first()?.accessToken.orEmpty())
            },
            resultAction = {
                ProfileInfo(
                    it.data.id,
                    it.data.type,
                    UserAttributesInfo(
                        it.data.attributes.email,
                        it.data.attributes.name,
                        it.data.attributes.avatarUrl
                    )
                )
            }
        )
}