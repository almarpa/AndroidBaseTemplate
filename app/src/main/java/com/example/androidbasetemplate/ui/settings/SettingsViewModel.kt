package com.example.androidbasetemplate.ui.settings

import androidx.lifecycle.ViewModel
import com.example.androidbasetemplate.entity.enums.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _themeState = MutableStateFlow(AppTheme.AUTO)
    val themeState: StateFlow<AppTheme> = _themeState

    fun setCurrentTheme(isDarkMode: Boolean) {
        _themeState.tryEmit(
            if (isDarkMode) {
                AppTheme.DARK
            } else {
                AppTheme.LIGHT
            }
        )
    }
}
