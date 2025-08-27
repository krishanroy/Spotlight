package com.krishan.spotlight.presentation.ui.detail

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.krishan.spotlight.R
import com.krishan.spotlight.domain.model.Article
import com.krishan.spotlight.domain.util.formatRelativeTimeLocalized
import com.krishan.spotlight.presentation.ui.common.FeaturedScreenItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    context: Context,
    article: Article,
    uiState: DetailsUiState,
    onAction: (DetailUiAction) -> Unit,
    uiEffect: SharedFlow<DetailUiEffect>,
    onNavigateBack: () -> Unit = {}
) {

    LaunchedEffect(key1 = article) {
        onAction(DetailUiAction.LoadArticleDetail(article = article))
    }

    // collectLatest makes sure that we wont launch the same intent twice which is important
    // as user might hit the same browser icon multiple times and we wanna open the browser only once.
    LaunchedEffect(key1 = Unit) {
        uiEffect.collectLatest { uiEffect ->
            when (uiEffect) {
                DetailUiEffect.NavigateBack -> onNavigateBack.invoke()
                DetailUiEffect.OpenArticleUrlInABrowser -> {
                    val intent = Intent(Intent.ACTION_VIEW, article.url.orEmpty().toUri())
                    context.startActivity(intent)
                }

                DetailUiEffect.ShareArticle -> {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, article.url)
                        type = "text/plain"
                    }

                    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_via)))
                }
            }
        }
    }
    Scaffold(
        topBar = {
            DetailsTopAppBar(
                onNavigateBack = { onAction(DetailUiAction.OnBackIconClicked) },
                onShareArticle = { onAction(DetailUiAction.OnShareClicked) },
                onOpenUrlInABrowser = { onAction(DetailUiAction.OnBrowserClicked) })
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsTopAppBar(
    onNavigateBack: () -> Unit,
    onShareArticle: () -> Unit,
    onOpenUrlInABrowser: () -> Unit
) {
    TopAppBar(title = { Text(stringResource(R.string.details)) }, navigationIcon = {
        IconButton(onClick = { onNavigateBack.invoke() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }, actions = {
        IconButton(onClick = { onShareArticle.invoke() }) {
            Icon(painter = painterResource(R.drawable.share), contentDescription = "share article")
        }
        IconButton(onClick = { onOpenUrlInABrowser.invoke() }) {
            Icon(painter = painterResource(R.drawable.browser), contentDescription = "open url in a browser")
        }
    })
}

