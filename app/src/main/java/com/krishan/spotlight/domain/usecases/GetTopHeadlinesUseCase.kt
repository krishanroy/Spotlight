package com.krishan.spotlight.domain.usecases

import com.krishan.spotlight.domain.Resource
import com.krishan.spotlight.domain.model.News
import com.krishan.spotlight.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(
        country: String?,
        category: String?,
        sources: String?,
        query: String?,
        pageSize: Int?,
        page: Int?
    ): Flow<Resource<News>> = repository.getTopHeadlines(
        country = country,
        category = category,
        sources = sources,
        query = query,
        pageSize = pageSize,
        page = page
    )
}