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
import com.example.androidbasetemplate.ui.common.navigation.TemplateDestinations
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Bottom App Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun TemplateBottomAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    currentRoute: String = TemplateDestinations.POKEMON_LIST_ROUTE,
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
                    if(currentRoute == TemplateDestinations.POKEMON_LIST_ROUTE) {
                        Icons.AutoMirrored.Filled.List
                    } else {
                        Icons.AutoMirrored.Outlined.List
                    },
                    contentDescription = "List"
                )
            },
            label = { Text(stringResource(R.string.favorites_title)) },
            selected = currentRoute == TemplateDestinations.POKEMON_LIST_ROUTE,
            onClick = {
                navigationActions?.navigateToPokemonList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    if(currentRoute == TemplateDestinations.FAVORITE_LIST_ROUTE) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.Favorite
                    },
                    contentDescription = "Favorites"
                )
            },
            label = { Text(stringResource(R.string.favorites_title)) },
            selected = currentRoute == TemplateDestinations.FAVORITE_LIST_ROUTE,
            onClick = {
                navigationActions?.navigateToFavoriteList?.invoke()
                coroutineScope.launch { drawerState.close() }
            }
        )
    }

//    BottomAppBar(
//        modifier = Modifier
//            .height(65.dp)
//            .background(color = Color.Transparent)
//            .clip(shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)),
//        contentColor = MaterialTheme.colorScheme.onBackground,
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(4.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            IconButton(
//                onClick = {
//                    navigationActions?.navigateToPokemonList?.invoke()
//                    coroutineScope.launch {
//                        drawerState.close()
//                    }
//                },
//            ) {
//                Icon(
//                    Icons.AutoMirrored.Filled.List,
//                    contentDescription = "Home",
//                    tint = if (currentRoute == TemplateDestinations.POKEMON_LIST_ROUTE) {
//                        MaterialTheme.colorScheme.primary
//                    } else {
//                        Color.Unspecified
//                    },
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .fillMaxWidth(),
//                )
//            }
//            IconButton(
//                onClick = {
//                    navigationActions?.navigateToFavoriteList?.invoke()
//                    coroutineScope.launch {
//                        drawerState.close()
//                    }
//                },
//            ) {
//                Icon(
//                    Icons.Filled.Favorite,
//                    contentDescription = "Favorites",
//                    tint = if (currentRoute == TemplateDestinations.FAVORITE_LIST_ROUTE) {
//                        MaterialTheme.colorScheme.primary
//                    } else {
//                        Color.Unspecified
//                    },
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .fillMaxWidth(),
//                )
//            }
//        }
//    }
}
