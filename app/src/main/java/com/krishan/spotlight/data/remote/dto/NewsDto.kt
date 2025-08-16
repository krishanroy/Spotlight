package com.krishan.spotlight.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    @SerialName("status")
    val status: String?,
    @SerialName("totalResults")
    val totalResults: String?,
    @SerialName("articles")
    val articles: List<ArticleDto?>?
)

@Serializable
data class ArticleDto(
    @SerialName("source")
    val source: Source?,
    @SerialName("author")
    val author: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: Source?,
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
data class Source(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
)
