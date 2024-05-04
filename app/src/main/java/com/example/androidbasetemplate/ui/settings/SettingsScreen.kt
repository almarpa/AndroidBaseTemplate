package com.example.androidbasetemplate.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.enums.AppTheme
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(title = R.string.settings_title) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        val themeState by settingsViewModel.themeState.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp)
                .wrapContentSize()
                .fillMaxWidth(),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.dark_mode),
                    style = MaterialTheme.typography.titleLarge
                )
                Switch(
                    checked = themeState == AppTheme.DARK,
                    onCheckedChange = { settingsViewModel.setCurrentTheme(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .height(2.dp)
            )
        }
    }
}