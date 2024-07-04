package com.example.androidtemplateapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidtemplateapp.ui.TemplateApp
import com.example.androidtemplateapp.ui.settings.SettingsViewModel
import com.example.androidtemplateapp.ui.theme.TemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val themeUserSetting by settingsViewModel.userTheme.collectAsStateWithLifecycle()

            TemplateTheme(themeUserSetting) {
                TemplateApp()
            }
        }
    }
}
