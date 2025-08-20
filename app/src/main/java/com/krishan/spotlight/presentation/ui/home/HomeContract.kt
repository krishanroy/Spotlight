package com.krishan.spotlight.presentation.ui.home

import com.krishan.spotlight.domain.model.Article

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val articles: List<Article> = emptyList(),
    val featuredArticle: Article = Article(),
    val selectedCountry: String? = "US",
    val selectedCategory: String? = "technology",
    val categoryMap: Map<String, String> = mapOf(
        "technology" to "Technology",
        "business" to "Business",
        "entertainment" to "Entertainment",
        "general" to "General",
        "health" to "Health",
        "science" to "Science",
        "sports" to "Sports"
    )
)

sealed class HomeUiAction {
    data class OnCategorySelected(val category: String) : HomeUiAction()
    data class OnArticleClicked(val article: Article) : HomeUiAction()
}

sealed class HomeUiEffect {
    data class NavigateToDetail(val article: Article) : HomeUiEffect()
}
