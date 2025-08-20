package com.krishan.spotlight.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.krishan.spotlight.domain.model.Article

@Composable
fun FeaturedScreenItem(
    paddingValues: PaddingValues,
    article: Article,
    bottomContent: @Composable (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        AsyncImage(
            model = article.urlToImage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .aspectRatio(16 / 9f)
                .clip(RoundedCornerShape(8.dp)),
            contentDescription = "Details Hero Image",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            article.title.orEmpty(),
            modifier = Modifier.padding(horizontal = 12.dp),
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        bottomContent.invoke(article)
    }
}