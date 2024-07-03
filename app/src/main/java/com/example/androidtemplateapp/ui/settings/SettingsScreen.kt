package com.example.androidtemplateapp.ui.settings

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
import androidx.compose.ui.unit.sp
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.common.utils.setAppLanguage
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
    onThemeChange: (Boolean) -> Unit,
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
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .height(1.dp)
                    )
                    LanguagesSection(
                        languages = locales,
                        currentLanguage = locales[uiState.userData.locale]!!,
                        onLanguageChange = {
                            onLanguageChange(it)
                            setAppLanguage(it)
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .height(1.dp)
                    )
                    DarkModeSection(
                        themeState = uiState.userData.theme,
                        onChange = { isChecked -> onThemeChange(isChecked) },
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .height(1.dp)
                    )
                    AboutSection()
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .height(1.dp)
                    )
                }
            }

            else -> {
                // Do nothing
            }
        }
    }
}

@Composable
fun LanguagesSection(
    languages: Map<String, Int>,
    currentLanguage: Int,
    onLanguageChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(end = 40.dp),
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
        )
        CustomDropdown(
            items = languages,
            selected = currentLanguage,
            onClickItem = { selection -> onLanguageChange(selection) }
        )
    }
}

@Composable
fun DarkModeSection(themeState: AppTheme, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.dark_mode),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
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
fun AboutSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
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
            onThemeChange = {},
            onBackPressed = {},
            locales = mapOf(LocaleEnum.EN.name to R.string.language_english),
            onLanguageChange = {}
        )
    }
}
