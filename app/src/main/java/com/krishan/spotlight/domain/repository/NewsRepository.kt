package com.krishan.spotlight.domain.repository

import com.krishan.spotlight.domain.Resource
import com.krishan.spotlight.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(
        country: String?,
        category: String?,
        sources: String?,
        query: String?,
        pageSize: Int?,
        page: Int?
    ): Flow<Resource<News>>

    fun getEverything(
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
    ): Flow<Resource<News>>
}