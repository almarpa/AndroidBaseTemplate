package com.example.androidbasetemplate

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidbasetemplate.ui.TemplateApp
import com.example.androidbasetemplate.ui.settings.SettingsViewModel
import com.example.androidbasetemplate.ui.theme.TemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val themeUserSetting by settingsViewModel.themeState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) { settingsViewModel.getUserAppTheme() }
            TemplateTheme(themeUserSetting) {
                TemplateApp()
            }
        }
    }
}
