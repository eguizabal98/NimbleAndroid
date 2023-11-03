package com.eem.domain.interactor.survey

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.survey.SurveyDetails
import com.eem.domain.repository.survey.SurveyRepository
import javax.inject.Inject

class GetSurveyDetailsUseCaseImpl @Inject constructor(
    private val surveyRepository: SurveyRepository
) : GetSurveyDetailsUseCase {
    override suspend fun invoke(surveyId: String): ResultWrapper<SurveyDetails> =
        surveyRepository.getSurveyDetails(surveyId)
}
