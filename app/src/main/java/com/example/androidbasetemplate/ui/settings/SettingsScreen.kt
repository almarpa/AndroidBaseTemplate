package com.example.androidbasetemplate.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
    drawerState: DrawerState,
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(drawerState = drawerState, title = R.string.settings_title) {
                navController.popBackStack()
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // TODO : add background icon
            }
        }
    )
}
