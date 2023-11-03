package com.eem.domain.model.survey

data class SurveyDetails(
    var surveyData: SurveyData,
    var included: List<Included> = emptyList()
)

data class Included(
    var id: String,
    var type: String,
    var attributes: Attributes,
    var relationships: AnswerRelationships
)

data class Attributes(
    var text: String = "",
    var helpText: String = "",
    var displayOrder: Int = 0,
    var shortText: String = "",
    var pick: String = "",
    var displayType: String = "",
    var isMandatory: Boolean = false,
    var correctAnswerId: String = "",
    var facebookProfile: String = "",
    var twitterProfile: String = "",
    var imageUrl: String = "",
    var coverImageUrl: String = "",
    var coverImageOpacity: Double = 0.0,
    var coverBackgroundColor: String = "",
    var isShareableOnFacebook: Boolean = false,
    var isShareableOnTwitter: Boolean = false,
    var fontFace: String = "",
    var fontSize: String = "",
    var tagList: String = ""
)

data class AnswerRelationships(
    val surveyAnswers: SurveyAnswers
)

data class SurveyAnswers(
    val data: List<AnswerData> = emptyList()
)

data class AnswerData(
    val id: String,
    val type: String
)
