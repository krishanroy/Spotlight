package com.krishan.spotlight.data.remote.api

import com.krishan.spotlight.data.remote.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("sources") sources: String?,
        @Query("q") query: String?,
        @Query("pageSize") pageSize: String?,
        @Query("page") page: String?
    ): NewsDto

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String?,
        @Query("searchIn") searchIn: String?,
        @Query("sources") sources: String?,
        @Query("domains") domains: String?,
        @Query("excludeDomains") excludeDomains: String?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("language") language: String?,
        @Query("sortBy") sortBy: String?,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?,
    ): NewsDto

}