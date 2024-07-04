package com.example.androidtemplateapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.domain.UserDataUseCase
import com.example.androidtemplateapp.entity.UserData
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.entity.enums.LocaleEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val userData: UserData) : SettingsUiState
}

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userDataUseCase: UserDataUseCase) :
    ViewModel() {

    private val _userLocale: MutableStateFlow<String?> = MutableStateFlow(null)

    private val _userTheme = MutableStateFlow(AppTheme.AUTO)
    val userTheme: StateFlow<AppTheme> = _userTheme

    val settingsUiState = combine(
        _userLocale,
        _userTheme,
    ) { locale: String?, theme: AppTheme ->
        locale?.let {
            SettingsUiState.Success(UserData(it, theme))
        } ?: SettingsUiState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SettingsUiState.Loading
    )

    private val _locales: Map<String, Int> = getAppLocales()
    val locales: Map<String, Int> = _locales

    init {
        getUserAppData()
    }
    
    private fun getUserAppData() {
        getUserAppLocale()
        getUserAppTheme()
    }

    private fun getUserAppLocale() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataUseCase.getAppLocale()
                .catch {
                    _userLocale.tryEmit(null)
                }
                .collect { appLocale ->
                    _userLocale.tryEmit(appLocale)
                }
        }
    }

    fun setUserAppLocale(newLocale: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataUseCase.setAppLocale(newLocale)
        }
    }

    fun getUserAppTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            userDataUseCase.getAppTheme()
                .catch {
                    _userTheme.tryEmit(AppTheme.AUTO)
                }
                .collect { appTheme ->
                    _userTheme.tryEmit(appTheme)
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

    private fun getAppLocales() =
        mapOf(
            LocaleEnum.EN.value to R.string.language_english,
            LocaleEnum.ES.value to R.string.language_spanish
        )
}
