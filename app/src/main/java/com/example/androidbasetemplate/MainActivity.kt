package com.example.androidbasetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.androidbasetemplate.common.utils.getViewModel
import com.example.androidbasetemplate.ui.TemplateApp
import com.example.androidbasetemplate.ui.settings.SettingsViewModel
import com.example.androidbasetemplate.ui.theme.TemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel: SettingsViewModel = getViewModel<SettingsViewModel>()
            val themeUserSetting by settingsViewModel.themeState.collectAsState()
            
            TemplateTheme(themeUserSetting) {
                TemplateApp()
            }
        }
    }
}
