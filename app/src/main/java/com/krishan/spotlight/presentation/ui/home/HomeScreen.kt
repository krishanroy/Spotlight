package com.krishan.spotlight.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishan.spotlight.domain.model.Article
import com.krishan.spotlight.domain.util.formatRelativeTimeLocalized
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
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(), shape = RoundedCornerShape(4.dp), onClick = {
                            onAction(HomeUiAction.OnArticleClicked(article))
                        }) {
                        Row(Modifier.padding(12.dp)) {
                            AsyncImage(
                                model = article.urlToImage,
                                contentDescription = "Home Page Article image",
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(end = 12.dp)
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    ),
                                contentScale = ContentScale.Crop,
                            )
                            Column {
                                Text(
                                    article.title.orEmpty(),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Serif
                                    )
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Text(article.author.orEmpty(), style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500))
                                Spacer(modifier = Modifier.height(14.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(article.source?.name.orEmpty(), style = TextStyle(fontSize = 10.sp))
                                    Text(
                                        formatRelativeTimeLocalized(article.publishedAt.orEmpty()),
                                        style = TextStyle(fontSize = 10.sp)
                                    )
                                }
                            }
                        }
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