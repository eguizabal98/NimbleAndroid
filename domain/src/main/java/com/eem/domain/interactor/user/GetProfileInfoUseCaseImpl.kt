package com.eem.domain.interactor.user

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.user.ProfileInfo
import com.eem.domain.repository.user.UserRepository
import javax.inject.Inject

class GetProfileInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetProfileInfoUseCase {
    override suspend fun invoke(): ResultWrapper<ProfileInfo> = userRepository.getProfileInfo()
}
