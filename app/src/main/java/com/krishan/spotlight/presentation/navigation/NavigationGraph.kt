package com.krishan.spotlight.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.krishan.spotlight.domain.model.Article
import com.krishan.spotlight.presentation.ui.detail.DetailScreen
import com.krishan.spotlight.presentation.ui.detail.DetailViewModel
import com.krishan.spotlight.presentation.ui.detail.DetailsUiState
import com.krishan.spotlight.presentation.ui.home.HomeScreen
import com.krishan.spotlight.presentation.ui.home.HomeUiState
import com.krishan.spotlight.presentation.ui.home.HomeViewModel
import kotlin.reflect.typeOf

@Composable
fun NavigationGraph(navController: NavHostController, startDestination: Screen = Screen.Home) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable<Screen.Home> {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState: HomeUiState = viewModel.homeUiStateFlow.collectAsStateWithLifecycle().value
            val uiEffect = viewModel.homeUiEffectSharedFlow
            HomeScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::handleUiAction,
                onArticleClicked = { article -> navController.navigate(Screen.Detail(article)) })
        }

        composable<Screen.Detail>(typeMap = mapOf(typeOf<Article>() to serializableType<Article>())) { backStackEntry ->
            val article: Article = requireNotNull(backStackEntry.toRoute<Screen.Detail>()).article
            val viewModel: DetailViewModel = hiltViewModel()
            val uiState: DetailsUiState = viewModel.articleDetailStateFlow.collectAsStateWithLifecycle().value
            DetailScreen(article = article, onNavigateBack = { navController.popBackStack() })
        }
    }
}