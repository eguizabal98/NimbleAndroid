package com.eem.domain.repository.user

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.user.ProfileInfo

interface UserRepository {
    suspend fun getProfileInfo(): ResultWrapper<ProfileInfo>
}