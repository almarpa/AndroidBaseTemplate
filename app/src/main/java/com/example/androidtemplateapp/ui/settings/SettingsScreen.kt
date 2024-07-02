package com.example.androidtemplateapp.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.UserData
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.entity.enums.LocaleEnum
import com.example.androidtemplateapp.ui.common.dropdown.CustomDropdown
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.topappbar.DefaultTopAppBar

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    locales: Map<String, Int>,
    onLanguageChange: (String) -> Unit,
    onThemeChanged: (Boolean) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(title = R.string.settings_title) { onBackPressed() }
        }
    ) { paddingValues ->
        when (uiState) {
            is SettingsUiState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(top = 16.dp)
                        .wrapContentSize()
                        .fillMaxWidth(),
                ) {
                    LanguagesSection(
                        languages = locales,
                        currentLanguage = locales[uiState.userData.locale]!!,
                        onLanguageChange = { onLanguageChange(it) }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .height(2.dp)
                    )
                    DarkModeSection(
                        themeState = uiState.userData.theme,
                        onChange = { isChecked -> onThemeChanged(isChecked) },
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .height(2.dp)
                    )
                    AboutSection()
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .height(2.dp)
                    )
                }
            }

            SettingsUiState.Error -> {}// TODO()
            SettingsUiState.Loading -> {}// TODO()
        }
    }
}

@Composable
fun DarkModeSection(themeState: AppTheme, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.dark_mode),
            style = MaterialTheme.typography.titleMedium
        )
        Switch(
            checked = themeState == AppTheme.DARK,
            onCheckedChange = { onChange(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
fun LanguagesSection(
    languages: Map<String, Int>,
    currentLanguage: Int,
    onLanguageChange: (String) -> Unit,
) {
    val availableLanguages = languages.mapValues { stringResource(it.value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CustomDropdown(
            items = availableLanguages,
            selected = stringResource(id = currentLanguage),
            onClickItem = { selection ->
                setAppLanguage(selection)
                onLanguageChange(selection)
            }
        )
    }
}

fun setAppLanguage(locale: String?) {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
}

@Composable
fun AboutSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun SettingsScreenPreview() {
    TemplatePreviewTheme {
        SettingsScreen(
            uiState = SettingsUiState.Success(
                userData = UserData(
                    locale = LocaleEnum.EN.name,
                    theme = AppTheme.DARK
                )
            ),
            onThemeChanged = {},
            onBackPressed = {},
            locales = mapOf(),
            onLanguageChange = {}
        )
    }
}
