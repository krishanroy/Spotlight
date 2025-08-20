package com.krishan.spotlight.presentation.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {
    private val _mutableStateFlow: MutableStateFlow<DetailsUiState> = MutableStateFlow(DetailsUiState())
    val articleDetailStateFlow: StateFlow<DetailsUiState> = _mutableStateFlow

    private val _uiEffectMutableSharedFlow = MutableSharedFlow<DetailUiEffect>()
    val articleDetailUiEffectSharedFlow: SharedFlow<DetailUiEffect> = _uiEffectMutableSharedFlow
}