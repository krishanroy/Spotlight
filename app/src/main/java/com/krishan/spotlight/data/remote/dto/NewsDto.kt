package com.krishan.spotlight.data.remote.dto

import com.krishan.spotlight.domain.model.Article
import com.krishan.spotlight.domain.model.News
import com.krishan.spotlight.domain.model.Source
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.throwMissingFieldException

@Serializable
data class NewsDto(
    @SerialName("status")
    val status: String?,
    @SerialName("totalResults")
    val totalResults: Int?,
    @SerialName("articles")
    val articles: List<ArticleDto?>?
)

@Serializable
data class ArticleDto(
    @SerialName("source")
    val source: SourceDto?,
    @SerialName("author")
    val author: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("urlToImage")
    val urlToImage: String?,
    @SerialName("publishedAt")
    val publishedAt: String?,
    @SerialName("content")
    val content: String?,
)

@Serializable
data class SourceDto(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
)

fun NewsDto.toDomain(): News =
    News(status = status, totalResults = totalResults, articles = articles?.map { it?.toDomain() })

// Always making sure with the product or the BE what is the use of the id and what is the consequence of it being null
// is a good idea so that no error gets unreported. At the same time, error logging websites should not get spammed.
fun SourceDto.toDomain(): Source = Source(id = id.orEmpty(), name = name.orEmpty())

fun ArticleDto.toDomain(): Article {
    return Article(
        source = source?.toDomain(),
        author = author.orEmpty(),
        description = description.orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty()
    )
}
