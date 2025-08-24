package com.krishan.spotlight.presentation.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishan.spotlight.R
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
    LaunchedEffect(key1 = Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToDetail -> onArticleClicked(effect.article)
            }

        }
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Home")
        })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CategoryTab(
                categoryMap = uiState.categoryMap,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { selectedCategory ->
                    onAction(
                        HomeUiAction.OnCategorySelected(selectedCategory)
                    )
                })
            when {
                uiState.isLoading -> Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }

                uiState.error != null -> Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxSize()

                ) {
                    Text("Something went wrong")
                }

                uiState.articles.isNotEmpty() -> HomeScreenList(
                    articles = uiState.articles,
                    selectedArticle = uiState.featuredArticle,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun HomeScreenList(
    articles: List<Article>,
    selectedArticle: Article,
    onAction: (HomeUiAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            HomeFeaturedItemSection(selectedArticle, onAction)
        }
        item {
            Text(
                "Latest News",
                modifier = Modifier.padding(start = 12.dp),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W500)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        items(articles) { article ->
            LatestNewsSection(article = article, onAction = onAction)
        }
    }
}

@Composable
fun CategoryTab(
    categoryMap: Map<String, String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    ScrollableTabRow(
        edgePadding = 12.dp,
        selectedTabIndex = categoryMap.keys.indexOf(selectedCategory),
        modifier = Modifier.fillMaxWidth()
    ) {
        categoryMap.forEach { (category, label) ->
            Tab(selected = category == selectedCategory, onClick = { onCategorySelected(category) }) {
                Text(text = label, modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp))
            }
        }
    }
}

@Composable
private fun HomeFeaturedItemSection(
    selectedArticle: Article,
    onAction: (HomeUiAction) -> Unit
) {
    FeaturedScreenItem(article = selectedArticle, paddingValues = PaddingValues(top = 12.dp)) { article ->
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            article.description.orEmpty(),
            modifier = Modifier.padding(horizontal = 12.dp),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W300)
        )
        Spacer(modifier = Modifier.height(10.dp))
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
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun LatestNewsSection(
    article: Article,
    onAction: (HomeUiAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Ensures all children match tallest height
            .padding(vertical = 4.dp, horizontal = 12.dp)
            .clickable(onClick = {
                onAction(HomeUiAction.OnArticleClicked(article))
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = article.title.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.source?.name.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = formatRelativeTimeLocalized(isoString = article.publishedAt.orEmpty()),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}