package com.krishan.spotlight.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishan.spotlight.domain.Resource
import com.krishan.spotlight.domain.model.Article
import com.krishan.spotlight.domain.model.News
import com.krishan.spotlight.domain.usecases.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase) : ViewModel() {
    private val _homeUiMutableStateFlow = MutableStateFlow(HomeUiState())
    val homeUiStateFlow = _homeUiMutableStateFlow.asStateFlow().onStart {
        fetchNews()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
        initialValue = HomeUiState()
    )

    private val _homeUiEffectMutableSharedFlow = MutableSharedFlow<HomeUiEffect>()
    val homeUiEffectSharedFlow: SharedFlow<HomeUiEffect> = _homeUiEffectMutableSharedFlow
    fun handleUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnCategorySelected -> updateSelectedCategory(action.category)
            is HomeUiAction.OnArticleClicked -> navigateToDetail(action.article)
        }
    }

    private fun updateSelectedCategory(category: String) {
        // TODO
    }

    private fun navigateToDetail(article: Article) {
        viewModelScope.launch {
            _homeUiEffectMutableSharedFlow.emit(HomeUiEffect.NavigateToDetail(article = article))
        }
    }

    private fun fetchNews() =
        getTopHeadlinesUseCase(
            country = _homeUiMutableStateFlow.value.selectedCountry,
            category = _homeUiMutableStateFlow.value.selectedCategory,
            sources = null,
            query = null,
            pageSize = null,
            page = null
        ).onEach { result: Resource<News> ->
            when (result) {
                Resource.Loading -> updateUiState { copy(isLoading = true) }
                is Resource.Error -> updateUiState { copy(isLoading = false, error = result.message) }
                is Resource.Success -> updateUiState {
                    copy(
                        isLoading = false,
                        articles = result.data.articles?.filterNotNull() ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)


    /**
     * we are generally used to writing  private fun updateUiState (block: (HomeUiState) -> HomeUiState){}
     * In that case we have to specify state to call state.copy()
     * updateUiState { state ->
     *     state.copy(isLoading = true, error = null)
     * }
     *
     * block: HomeUiState.() -> HomeUiState is an example of LAMBDA WITH RECEIVER, meaning
     * inside that lambda, I can access members of HomeUiState directly like
     *     updateUiState {
     *        copy(isLoading = true, error = null)
     *     }
     */
    private fun updateUiState(block: HomeUiState.() -> HomeUiState) {
        _homeUiMutableStateFlow.update(block)
    }
}