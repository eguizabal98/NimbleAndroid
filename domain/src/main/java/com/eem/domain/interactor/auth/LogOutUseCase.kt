package com.eem.domain.interactor.auth

import com.eem.domain.model.base.ResultWrapper

interface LogOutUseCase{
    suspend operator fun invoke(): ResultWrapper<Boolean>
}