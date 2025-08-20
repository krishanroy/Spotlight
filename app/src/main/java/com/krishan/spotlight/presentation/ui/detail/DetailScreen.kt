package com.krishan.spotlight.presentation.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krishan.spotlight.domain.model.Article
import com.krishan.spotlight.domain.util.formatRelativeTimeLocalized
import com.krishan.spotlight.presentation.ui.common.FeaturedScreenItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(article: Article, onNavigateBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Details")
            }, navigationIcon = {
                IconButton(onClick = { onNavigateBack.invoke() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            })
        },
    ) { paddingValues ->
        FeaturedScreenItem(paddingValues, article) {
            Spacer(modifier = Modifier.size(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    article.source?.name.orEmpty(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    formatRelativeTimeLocalized(article.publishedAt.orEmpty()),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            Text(
                article.description.orEmpty(),
                modifier = Modifier.padding(horizontal = 12.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W500)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                article.content.orEmpty(),
                modifier = Modifier.padding(horizontal = 12.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W300)
            )
        }
    }
}

