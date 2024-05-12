package com.example.androidbasetemplate.ui.favorites

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListScreen(
    favoriteListViewModel: FavoriteListViewModel = hiltViewModel(),
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    Scaffold(
        topBar = {
            DrawerTopAppBar(drawerState = drawerState, title = R.string.favorites_title)
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
        },
        bottomBar = {
            TemplateBottomAppBar(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions
            )
        }
    )
}
