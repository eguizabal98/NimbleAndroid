package com.eem.domain.interactor.auth

import com.eem.domain.model.base.ResultWrapper

interface IsUserLoginUseCase {
    suspend operator fun invoke(): ResultWrapper<Boolean>
}
