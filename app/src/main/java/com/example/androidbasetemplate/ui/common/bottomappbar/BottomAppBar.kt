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
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.androidbasetemplate.ui.theme.TemplateTheme
import kotlinx.coroutines.launch

@Composable
fun TemplateBottomAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    currentRoute: String = Routes.PokemonList.route,
    navigationActions: NavigationActions? = null,
) {
    val coroutineScope = rememberCoroutineScope()

    // TODO: migrate to Material 3 NavigationBar
    BottomNavigation(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(vertical = 4.dp)
            .clip(
                RoundedCornerShape(20.dp)
            ),
        backgroundColor = MaterialTheme.colorScheme.surface,
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    if (currentRoute == Routes.PokemonList.route) {
                        Icons.AutoMirrored.Filled.List
                    } else {
                        Icons.AutoMirrored.Outlined.List
                    },
                    contentDescription = "List",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            label = {
                Text(
                    stringResource(R.string.pokedex_title),
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            selected = currentRoute == Routes.PokemonList.route,
            selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onClick = {
                navigationActions?.navigateToPokemonList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    if (currentRoute == Routes.Favorites.route) {
                        Icons.Outlined.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Favorites",
                )
            },
            label = {
                Text(
                    stringResource(R.string.favorites_title),
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            selected = currentRoute == Routes.Favorites.route,
            selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onClick = {
                navigationActions?.navigateToFavoriteList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
    }
}


@Composable
@Preview("Bottom App Bar")
@Preview("Bottom App Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TemplateBottomAppBarPreview() {
    TemplateTheme {
        TemplateBottomAppBar(
            drawerState = DrawerState(DrawerValue.Closed),
            Routes.PokemonList.route,
        )
    }
}