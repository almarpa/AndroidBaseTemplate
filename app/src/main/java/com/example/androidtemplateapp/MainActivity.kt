package com.example.androidtemplateapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidtemplateapp.entity.enums.AppTheme
import com.example.androidtemplateapp.ui.TemplateApp
import com.example.androidtemplateapp.ui.settings.SettingsViewModel
import com.example.androidtemplateapp.ui.theme.TemplateTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val themeUserSetting by settingsViewModel.userTheme.collectAsStateWithLifecycle()
            val systemUiController = rememberSystemUiController()
            
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = themeUserSetting == AppTheme.LIGHT
            )

            TemplateTheme(themeUserSetting) {
                TemplateApp()
            }
        }
    }
}
