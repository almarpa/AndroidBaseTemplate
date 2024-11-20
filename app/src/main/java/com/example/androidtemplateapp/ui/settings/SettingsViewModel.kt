package com.example.androidtemplateapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.domain.UserDataUseCase
import com.example.androidtemplateapp.entity.UserData
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.entity.enums.LocaleEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SettingsUiState {
    data class Success(val userData: UserData) : SettingsUiState
}

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userDataUseCase: UserDataUseCase) :
    ViewModel() {

    val userData = userDataUseCase.getUserData().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = UserData(locale = LocaleEnum.EN.value, theme = AppTheme.AUTO)
    )

    private val _locales: Map<String, Int> = getAppLocales()
    val locales: Map<String, Int> = _locales

    fun setUserAppLocale(newLocale: String) {
        viewModelScope.launch {
            userDataUseCase.setAppLocale(newLocale)
        }
    }

    fun setUserAppTheme(isChecked: Boolean) {
        viewModelScope.launch {
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
