package com.eem.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eem.data.network.api.SurveyApi
import com.eem.data.room.dao.AccessTokenDao
import com.eem.domain.model.survey.SurveyData
import retrofit2.HttpException
import java.io.IOException


class SurveyPagingSource(
    private val surveyApi: SurveyApi,
    private val accessTokenDao: AccessTokenDao
) : PagingSource<Int, SurveyData>() {

    override fun getRefreshKey(state: PagingState<Int, SurveyData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(INDEX_ONE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(INDEX_ONE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyData> {
        return try {
            val nextPage = params.key ?: INDEX_ONE // if params.key is null then fallback to page 1
            val token = accessTokenDao.getAll()?.first()?.accessToken.orEmpty()
            val response = surveyApi.getSurveyList(token, nextPage, ITEMS_PER_PAGE)

            var data = listOf<SurveyData>()
            var nextKey: Int? = null

            response.body()?.let {
                if ((it.surveyPagedInfo.page ?: 0) <= (it.surveyPagedInfo.pages ?: 0)) {
                    data = it.surveyListData.map { surveyData -> surveyData.toDomain() }
                    nextKey = it.surveyPagedInfo.page
                }
            }

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == INDEX_ONE) null else nextPage - INDEX_ONE,
                nextKey = nextKey?.plus(INDEX_ONE)
            )
        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
            LoadResult.Error(e)
        } catch (e: IOException) {
            Log.d(TAG, "IOException: ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException: ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INDEX_ONE = 1
        const val ITEMS_PER_PAGE = 20
        const val TAG = "SurveyPagingSource"
    }
}