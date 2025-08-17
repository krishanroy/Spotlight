package com.krishan.spotlight.presentation.ui.home

import androidx.compose.runtime.Composable
import com.krishan.spotlight.domain.model.Article
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
    uiEffect: SharedFlow<HomeUiEffect>,
    onArticleClicked: (Article) -> Unit
) {

}