package com.example.androidbasetemplate.ui.common.bottomappbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.TemplateDestinations
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Bottom App Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun TemplateBottomAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    currentRoute: String = TemplateDestinations.HOME_ROUTE,
    navigationActions: NavigationActions? = null,
) {
    val coroutineScope = rememberCoroutineScope()
    BottomAppBar(
        modifier = Modifier
            .height(60.dp)
            .background(color = Color.Transparent)
            .clip(shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)),
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = {
                    navigationActions?.navigateToPokemonList?.invoke()
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    Icons.Filled.List,
                    contentDescription = "Home",
                    tint = if (currentRoute == TemplateDestinations.POKEMON_LIST_ROUTE) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Unspecified
                    },
                )
            }
            IconButton(
                onClick = {
                    navigationActions?.navigateToHome?.invoke()
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "List",
                    tint = if (currentRoute == TemplateDestinations.HOME_ROUTE) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Unspecified
                    },
                )
            }
            IconButton(
                onClick = {
                    navigationActions?.navigateToFavoriteList?.invoke()
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorites",
                    tint = if (currentRoute == TemplateDestinations.FAVORITE_LIST_ROUTE) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Unspecified
                    },
                )
            }
        }
    }
}
