package com.eem.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eem.data.network.api.SurveyApi
import com.eem.data.network.model.CoroutineContextProvider
import com.eem.data.network.model.survey.Answer
import com.eem.data.network.model.survey.Questions
import com.eem.data.network.model.survey.SurveyAnswerBody
import com.eem.data.repository.SurveyPagingSource.Companion.ITEMS_PER_PAGE
import com.eem.data.room.dao.AccessTokenDao
import com.eem.domain.model.base.ResultWrapper
import com.eem.domain.model.survey.AnswerData
import com.eem.domain.model.survey.AnswerRelationships
import com.eem.domain.model.survey.Attributes
import com.eem.domain.model.survey.Included
import com.eem.domain.model.survey.QuestionData
import com.eem.domain.model.survey.SurveyAnswer
import com.eem.domain.model.survey.SurveyAnswers
import com.eem.domain.model.survey.SurveyAttributes
import com.eem.domain.model.survey.SurveyData
import com.eem.domain.model.survey.SurveyDetails
import com.eem.domain.model.survey.SurveyQuestions
import com.eem.domain.model.survey.SurveyRelationships
import com.eem.domain.repository.survey.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyApi: SurveyApi,
    private val accessTokenDao: AccessTokenDao,
    private val contextProvider: CoroutineContextProvider
): SurveyRepository {

    override suspend fun getSurveyList(): Flow<PagingData<SurveyData>> = Pager(PagingConfig(ITEMS_PER_PAGE)){
        SurveyPagingSource(surveyApi, accessTokenDao.getAll()?.first()?.accessToken.orEmpty())
    }.flow

    override suspend fun getSurveyDetails(surveyId: String): ResultWrapper<SurveyDetails> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                surveyApi.getSurveyDetails(
                    authorization = accessTokenDao.getAll()?.first()?.accessToken,
                    surveyId
                )
            },
            resultAction = {
                SurveyDetails(
                    surveyData = SurveyData(
                        it.surveyData.id.orEmpty(),
                        it.surveyData.type.orEmpty(),
                        SurveyAttributes(
                            it.surveyData.attributes.title.orEmpty(),
                            it.surveyData.attributes.description.orEmpty(),
                            it.surveyData.attributes.emailAboveData.orEmpty(),
                            it.surveyData.attributes.emailBellowData.orEmpty(),
                            it.surveyData.attributes.isActive.orEmpty(),
                            it.surveyData.attributes.imageUrl.orEmpty(),
                            it.surveyData.attributes.createdAt.orEmpty(),
                            it.surveyData.attributes.activeAt.orEmpty(),
                            it.surveyData.attributes.inactiveAt.orEmpty(),
                            it.surveyData.attributes.surveyType.orEmpty()
                        ),
                        SurveyRelationships(
                            SurveyQuestions(
                                it.surveyData.relationships.surveyQuestions.data.map { questionData ->
                                    QuestionData(questionData.id, questionData.type)
                                }
                            )
                        )
                    ),
                    included = it.included.map { includedAns ->
                        Included(includedAns.id, includedAns.type, Attributes(
                            includedAns.attributes.text,
                            includedAns.attributes.helpText,
                            includedAns.attributes.displayOrder,
                            includedAns.attributes.shortText,
                            includedAns.attributes.pick,
                            includedAns.attributes.displayType,
                            includedAns.attributes.isMandatory,
                            includedAns.attributes.correctAnswerId,
                            includedAns.attributes.facebookProfile,
                            includedAns.attributes.twitterProfile,
                            includedAns.attributes.imageUrl,
                            includedAns.attributes.coverImageUrl,
                            includedAns.attributes.coverImageOpacity,
                            includedAns.attributes.coverBackgroundColor,
                            includedAns.attributes.isShareableOnFacebook,
                            includedAns.attributes.isShareableOnTwitter,
                            includedAns.attributes.fontFace,
                            includedAns.attributes.fontSize,
                            includedAns.attributes.tagList
                        ), AnswerRelationships(
                            SurveyAnswers(
                                includedAns.relationships.surveyAnswers.data.map { answerData ->
                                    AnswerData(
                                        answerData.id,
                                        answerData.type
                                    )
                                }
                            )
                        ))
                    }
                )
            }
        )

    override suspend fun submitSurveyResponse(surveyAnswer: SurveyAnswer): ResultWrapper<Boolean> =
        executeApiCall(
            contextProvider = contextProvider,
            call = {
                surveyApi.submitSurveyResponse(
                    authorization = accessTokenDao.getAll()?.first()?.accessToken,
                    surveyAnswerBody = SurveyAnswerBody(
                        surveyAnswer.surveyId,
                        surveyAnswer.questions.map {
                            Questions(it.id,it.answers.map { answer ->
                                Answer(answer.id,answer.answer)
                            })
                        }
                    )
                )
            },
            resultAction = {
                true
            }
        )
}