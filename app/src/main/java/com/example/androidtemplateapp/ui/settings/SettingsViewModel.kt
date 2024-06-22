package com.example.androidtemplateapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplateapp.domain.UserDataUseCase
import com.example.androidtemplateapp.entity.enums.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userDataUseCase: UserDataUseCase) :
    ViewModel() {

    private val _themeState = MutableStateFlow(AppTheme.AUTO)
    val themeState: StateFlow<AppTheme> = _themeState
    
    fun getUserAppTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataUseCase.getAppTheme()
                .catch {
                    _themeState.tryEmit(AppTheme.AUTO)
                }
                .collect { appTheme ->
                    _themeState.tryEmit(appTheme)
                }
        }
    }

    fun setUserAppTheme(isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataUseCase.setAppTheme(
                if (isChecked) {
                    AppTheme.DARK
                } else {
                    AppTheme.LIGHT
                }
            )
        }
    }
}
