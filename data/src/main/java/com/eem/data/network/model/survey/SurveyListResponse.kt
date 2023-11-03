package com.eem.data.network.model.survey

import com.google.gson.annotations.SerializedName

data class SurveyListResponse(
    @SerializedName("data")
    val surveyListData: List<SurveyData> = emptyList(),
    @SerializedName("meta")
    val surveyPagedInfo: SurveyPagedInfo
)

data class SurveyPagedInfo(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("pages")
    val pages: Int? = 0,
    @SerializedName("page_size")
    val pageSize: Int? = 0,
    @SerializedName("records")
    val records: Int? = 0
)

data class SurveyData(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("attributes")
    val attributes: SurveyAttributes,
    @SerializedName("relationships")
    val relationships: SurveyRelationships
) {
    fun toDomain(): com.eem.domain.model.survey.SurveyData =
        com.eem.domain.model.survey.SurveyData(
            id.orEmpty(),
            type.orEmpty(),
            com.eem.domain.model.survey.SurveyAttributes(
                attributes.title,
                attributes.description,
                attributes.emailAboveData,
                attributes.emailBellowData,
                attributes.isActive,
                attributes.imageUrl,
                attributes.createdAt,
                attributes.activeAt,
                attributes.inactiveAt,
                attributes.surveyType
            ),
            com.eem.domain.model.survey.SurveyRelationships(
                com.eem.domain.model.survey.SurveyQuestions(
                    relationships.surveyQuestions.data.map {
                        com.eem.domain.model.survey.QuestionData(
                            it.id, it.type
                        )
                    }
                )
            )
        )
}

data class SurveyAttributes(
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("thank_email_above_threshold")
    val emailAboveData: String? = "",
    @SerializedName("thank_email_below_threshold")
    val emailBellowData: String? = "",
    @SerializedName("is_active")
    val isActive: String? = "",
    @SerializedName("cover_image_url")
    val imageUrl: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("active_at")
    val activeAt: String? = "",
    @SerializedName("inactive_at")
    val inactiveAt: String? = "",
    @SerializedName("survey_type")
    val surveyType: String? = ""
)

data class SurveyRelationships(
    @SerializedName("questions")
    val surveyQuestions: SurveyQuestions
)

data class SurveyQuestions(
    @SerializedName("data")
    val data: List<QuestionData> = emptyList()
)

data class QuestionData(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("type")
    val type: String? = ""
)
