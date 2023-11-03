package com.eem.domain.interactor.survey

import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.survey.SurveyAnswer

interface SubmitSurveyUseCase {
    suspend operator fun invoke(surveyAnswer: SurveyAnswer): ResultWrapper<Boolean>
}
