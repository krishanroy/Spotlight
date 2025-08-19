package com.krishan.spotlight.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.krishan.spotlight.presentation.ui.home.HomeScreen
import com.krishan.spotlight.presentation.ui.home.HomeUiState
import com.krishan.spotlight.presentation.ui.home.HomeViewModel

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
                onArticleClicked = { })
        }
    }
}