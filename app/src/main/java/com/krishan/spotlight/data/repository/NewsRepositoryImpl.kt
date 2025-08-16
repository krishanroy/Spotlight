package com.krishan.spotlight.data.repository

import com.krishan.spotlight.data.remote.api.NewsApi
import com.krishan.spotlight.data.remote.dto.ErrorDto
import com.krishan.spotlight.data.remote.dto.NewsDto
import com.krishan.spotlight.data.remote.dto.toDomain
import com.krishan.spotlight.domain.Resource
import com.krishan.spotlight.domain.model.News
import com.krishan.spotlight.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi) : NewsRepository {
    override fun getTopHeadlines(
        country: String?,
        category: String?,
        sources: String?,
        query: String?,
        pageSize: Int?,
        page: Int?
    ): Flow<Resource<News>> = flow {
        emit(Resource.Loading)
        try {
            val topHeadlines: NewsDto = newsApi.getTopHeadlines(
                country = country,
                category = category,
                sources = sources,
                query = query,
                pageSize = pageSize,
                page = page
            )
            emit(Resource.Success(data = topHeadlines.toDomain()))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val (apiErrorMessage, apiErrorCode) = parseErrorBody(errorBody)

            emit(
                Resource.Error(
                    message = apiErrorMessage ?: "An unexpected HTTP error occurred: ${e.code()}",
                    errorCode = apiErrorCode,
                    throwable = e
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Network connection error: Please check your internet connection.",
                    throwable = e
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "An unknown error occurred.",
                    throwable = e
                )
            )
        }
    }

    override fun getEverything(
        query: String?,
        searchIn: String?,
        sources: String?,
        domains: String?,
        excludeDomains: String?,
        from: String?,
        to: String?,
        language: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?
    ): Flow<Resource<News>> = flow {
        emit(value = Resource.Loading)

        try {
            val newsDto = newsApi.getEverything(
                query = query,
                searchIn = searchIn,
                sources = sources,
                domains = domains,
                excludeDomains = excludeDomains,
                from = from,
                to = to,
                language = language,
                sortBy = sortBy,
                pageSize = pageSize,
                page = page
            )

            emit(Resource.Success(newsDto.toDomain()))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val (apiErrorMessage, apiErrorCode) = parseErrorBody(errorBody)

            emit(
                Resource.Error(
                    message = apiErrorMessage ?: "An unexpected HTTP error occurred: ${e.code()}",
                    errorCode = apiErrorCode,
                    throwable = e
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Network connection error: Please check your internet connection.",
                    throwable = e
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "An unknown error occurred.",
                    throwable = e
                )
            )
        }
    }
}

private fun parseErrorBody(errorBody: ResponseBody?): Pair<String?, String?> {
    return try {
        val errorJsonString = errorBody?.string()

        if (!errorJsonString.isNullOrEmpty()) {
            val errorDto = Json.decodeFromString<ErrorDto>(errorJsonString) // change errorDto to a domain error model
            Pair(errorDto.message, errorDto.code)
        } else {
            Pair(null, null)
        }
    } catch (e: Exception) {
        Pair("The error details returned from the API could not be resolved: $e", null)
    } finally {
        errorBody?.close()
    }
}