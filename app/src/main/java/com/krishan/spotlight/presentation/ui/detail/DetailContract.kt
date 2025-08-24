package com.krishan.spotlight.presentation.ui.detail

import com.krishan.spotlight.domain.model.Article

data class DetailsUiState(val article: Article = Article())

sealed class DetailUiAction {
    data class LoadArticleDetail(val article: Article) : DetailUiAction()
    data object OnBrowserClicked : DetailUiAction()
    data object OnShareClicked : DetailUiAction()
    data object OnBackIconClicked : DetailUiAction()
}

sealed class DetailUiEffect {
    data object OpenArticleUrlInABrowser : DetailUiEffect()
    data object ShareArticle : DetailUiEffect()
    data object NavigateBack : DetailUiEffect()
}