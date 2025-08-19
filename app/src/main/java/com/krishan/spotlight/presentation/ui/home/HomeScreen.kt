package com.krishan.spotlight.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.krishan.spotlight.domain.model.Article
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
    uiEffect: SharedFlow<HomeUiEffect>,
    onArticleClicked: (Article) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Home")
        })
    }) { innerPadding ->
        // I need to call HomeViewModel
        LaunchedEffect(key1 = Unit) {
            uiEffect.collect { effect ->
                when (effect) {
                    is HomeUiEffect.NavigateToDetail -> onArticleClicked(effect.article)
                }

            }
        }
        when {
            uiState.isLoading -> CenteredContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator()
            }

            uiState.error != null -> CenteredContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text("Something went wrong")
            }

            uiState.articles.isNotEmpty() -> LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(uiState.articles) { article ->
                    Card(shape = RoundedCornerShape(4), onClick = {
                        onAction(HomeUiAction.OnArticleClicked(article))
                    }) {
                        Text(article.author.orEmpty())
                    }
                }
            }
        }
    }
}

@Composable
fun CenteredContent(modifier: Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        content()
    }
}