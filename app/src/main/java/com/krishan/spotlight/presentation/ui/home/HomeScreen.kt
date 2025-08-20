package com.krishan.spotlight.presentation.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishan.spotlight.domain.model.Article
import com.krishan.spotlight.domain.util.formatRelativeTimeLocalized
import com.krishan.spotlight.presentation.ui.common.FeaturedScreenItem
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
        LaunchedEffect(key1 = Unit) {
            uiEffect.collect { effect ->
                when (effect) {
                    is HomeUiEffect.NavigateToDetail -> onArticleClicked(effect.article)
                }

            }
        }
        when {
            uiState.isLoading -> Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator()
            }

            uiState.error != null -> Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text("Something went wrong")
            }

            uiState.articles.isNotEmpty() -> HomeScreenList(
                innerPadding = innerPadding,
                articles = uiState.articles,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun HomeScreenList(
    innerPadding: PaddingValues,
    articles: List<Article>,
    onAction: (HomeUiAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Featured Item
        item {
            FeaturedScreenItem(paddingValues = innerPadding, article = articles.filter { article ->
                article.description?.isNotEmpty() == true && article.urlToImage?.isNotEmpty() == true
            }.random()) { article ->
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    article.description.orEmpty(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W300)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(contentAlignment = Alignment.CenterEnd) {
                    Text(
                        "Read More..",
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .clickable(onClick = {
                                onAction(HomeUiAction.OnArticleClicked(article))
                            }),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
        // Latest News Title
        item {
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Latest News",
                modifier = Modifier.padding(start = 12.dp),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W500)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        // Latest News List
        items(articles) { article ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
                onClick = {
                    onAction(HomeUiAction.OnArticleClicked(article))
                }) {
                Row(Modifier.padding(horizontal = 12.dp)) {
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
                        Text(
                            article.author.orEmpty(),
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500)
                        )
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