package com.eem.data.network.api

import com.eem.data.network.model.EmptyResponse
import com.eem.data.network.model.survey.SurveyAnswerBody
import com.eem.data.network.model.survey.SurveyDetailsResponse
import com.eem.data.network.model.survey.SurveyListResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SurveyApi {

    @GET("api/v1/surveys")
    suspend fun getSurveyList(
        @Header("Authorization") authorization: String?,
        @Query("page[number]") page: Int,
        @Query("page[size]") size: Int
    ): Response<SurveyListResponseDto>

    @GET("api/v1/surveys/{surveyId}")
    suspend fun getSurveyDetails(
        @Header("Authorization") authorization: String?,
        @Path("surveyId") surveyId: String
    ): Response<SurveyDetailsResponse>

    @POST("api/v1/responses")
    suspend fun submitSurveyResponse(
        @Header("Authorization") authorization: String?,
        @Body surveyAnswerBody: SurveyAnswerBody
    ): Response<EmptyResponse>
}
