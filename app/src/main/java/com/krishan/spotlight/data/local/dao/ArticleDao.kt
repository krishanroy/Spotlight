package com.krishan.spotlight.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.krishan.spotlight.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    suspend fun insertArticle(articleEntity: ArticleEntity)

    @Query(value = "DELETE from articles WHERE url is :articleUrl")
    suspend fun deleteArticleByUrl(articleUrl: String): Unit

    @Query(value = "SELECT EXISTS(SELECT 1 from articles where url is :articleUrl LIMIT 1)")
    suspend fun isArticleBookMarked(articleUrl: String): Boolean

    @Query(value = "SELECT * FROM articles ORDER BY id  DESC")
    suspend fun getAllBookMarkedArticle(): Flow<List<ArticleEntity>>

    @Query(value = "DELETE FROM articles")
    suspend fun deleteAllBookmarkedArticles(): Unit
}