package com.example.androidtemplateapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidtemplateapp.entity.enums.AppThemeEnum
import com.example.androidtemplateapp.ui.TemplateApp
import com.example.androidtemplateapp.ui.settings.SettingsViewModel
import com.example.androidtemplateapp.ui.theme.TemplateTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setScreenOrientation()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val userData by settingsViewModel.userData.collectAsStateWithLifecycle()
            val systemUiController = rememberSystemUiController()

            TemplateTheme(userData.theme) {
                TemplateApp()
            }

            setStatusBarColorsByTheme(systemUiController, userData.theme)
        }
    }

    private fun setScreenOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }

    private fun setStatusBarColorsByTheme(
        systemUiController: SystemUiController,
        theme: AppThemeEnum,
    ) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = theme == AppThemeEnum.LIGHT
        )
    }
}
