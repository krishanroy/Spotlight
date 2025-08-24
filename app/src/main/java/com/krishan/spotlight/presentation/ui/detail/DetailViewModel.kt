package com.krishan.spotlight.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishan.spotlight.domain.model.Article
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val _mutableStateFlow: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState())
    val articleDetailStateFlow: StateFlow<DetailsUiState> = _mutableStateFlow

    private val _uiEffectMutableSharedFlow = MutableSharedFlow<DetailUiEffect>()
    val articleDetailUiEffectSharedFlow: SharedFlow<DetailUiEffect> = _uiEffectMutableSharedFlow

    fun handleUiAction(action: DetailUiAction) {
        when (action) {
            is DetailUiAction.LoadArticleDetail -> updateStateWithCurrentArticle(article = action.article)
            DetailUiAction.OnBackIconClicked -> navigateBack()
            DetailUiAction.OnBrowserClicked -> openArticleUrlInABrowser()
            DetailUiAction.OnShareClicked -> shareArticle()
        }
    }

    private fun shareArticle() =
        viewModelScope.launch {
            _uiEffectMutableSharedFlow.emit(DetailUiEffect.ShareArticle)
        }


    private fun openArticleUrlInABrowser() =
        viewModelScope.launch {
            _uiEffectMutableSharedFlow.emit(DetailUiEffect.OpenArticleUrlInABrowser)
        }


    private fun navigateBack() =
        viewModelScope.launch {
            _uiEffectMutableSharedFlow.emit(DetailUiEffect.NavigateBack)
        }


    private fun updateStateWithCurrentArticle(article: Article) {
        updateUiState {
            copy(article = article)
        }
    }

    private fun updateUiState(block: DetailsUiState.() -> DetailsUiState) {
        _mutableStateFlow.update(block)
    }
}