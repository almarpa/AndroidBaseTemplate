package com.example.androidbasetemplate.ui.common.bottomappbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Bottom App Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun TemplateBottomAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    currentRoute: String = Routes.PokemonList.route,
    navigationActions: NavigationActions? = null,
) {
    val coroutineScope = rememberCoroutineScope()

    BottomNavigation(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(20.dp)
        ),
        backgroundColor = Color.White,
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    if(currentRoute == Routes.PokemonList.route) {
                        Icons.AutoMirrored.Filled.List
                    } else {
                        Icons.AutoMirrored.Outlined.List
                    },
                    contentDescription = "List"
                )
            },
            label = { Text(stringResource(R.string.favorites_title)) },
            selected = currentRoute == Routes.PokemonList.route,
            onClick = {
                navigationActions?.navigateToPokemonList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    if(currentRoute == Routes.Favorites.route) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.Favorite
                    },
                    contentDescription = "Favorites"
                )
            },
            label = { Text(stringResource(R.string.favorites_title)) },
            selected = currentRoute == Routes.Favorites.route,
            onClick = {
                navigationActions?.navigateToFavoriteList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
    }
}
