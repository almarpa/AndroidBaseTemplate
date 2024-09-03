package com.example.androidtemplateapp.ui.settings

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
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
    userData: UserData?,
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
        userData?.let { userDataNotNull ->
            SettingsContent(
                modifier = Modifier.padding(paddingValues),
                userData = userDataNotNull,
                locales = locales,
                onLanguageChange = { onLanguageChange(it) },
                onThemeChange = { onThemeChange(it) },
            )
        }
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier,
    userData: UserData,
    locales: Map<String, Int>,
    onLanguageChange: (String) -> Unit,
    onThemeChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
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
            currentLanguage = locales.getOrDefault(userData.locale, R.string.language_english),
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
            themeState = userData.theme,
            onChange = { isChecked -> onThemeChange(isChecked) },
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .height(1.dp)
        )
    }
}

@Composable
fun LanguagesSection(
    languages: Map<String, Int>,
    currentLanguage: Int,
    onLanguageChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            CustomDropdown(
                items = languages,
                selected = currentLanguage,
                onClickItem = { selection -> onLanguageChange(selection) }
            )
        }
    }
}

@Composable
fun DarkModeSection(themeState: AppTheme, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.dark_mode),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.dark_mode_description),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 3
            )
        }

        Switch(
            checked = themeState == AppTheme.DARK,
            onCheckedChange = { onChange(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            ),
            thumbContent = {
                Icon(
                    imageVector = if (themeState == AppTheme.DARK) {
                        Icons.Filled.DarkMode
                    } else {
                        Icons.Filled.LightMode
                    },
                    tint = MaterialTheme.colorScheme.primaryContainer,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(name = "Pokemon Details Screen")
@Preview(name = "Tablet Pokemon Details Screen", device = Devices.TABLET)
@Composable
fun SettingsScreenPreview() {
    TemplatePreviewTheme {
        SettingsScreen(
            userData = UserData(
                locale = LocaleEnum.EN.name,
                theme = AppTheme.DARK
            ),
            onThemeChange = {},
            onBackPressed = {},
            locales = mapOf(LocaleEnum.EN.name to R.string.language_english),
            onLanguageChange = {}
        )
    }
}
