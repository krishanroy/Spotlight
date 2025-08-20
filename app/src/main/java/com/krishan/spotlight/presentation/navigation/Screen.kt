package com.krishan.spotlight.presentation.navigation

import com.krishan.spotlight.domain.model.Article
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class Detail(val article: Article) : Screen()
}