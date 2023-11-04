package com.eem.domain.interactor.user

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.user.ProfileInfo

interface GetProfileInfoUseCase {
    suspend operator fun invoke(): ResultWrapper<ProfileInfo>
}