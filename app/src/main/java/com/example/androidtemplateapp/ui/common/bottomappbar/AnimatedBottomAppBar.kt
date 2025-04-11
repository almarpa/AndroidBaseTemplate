package com.example.androidtemplateapp.ui.common.bottomappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ManageSearch
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.ui.common.navigation.Routes

@Composable
fun AnimatedBottomAppBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    currentRoute: Routes,
    onRouteSelected: (Routes) -> Unit,
) {
    val bottomAppBarItems = listOf(
        BottomAppBarItem(
            icon = {
                Icon(
                    Icons.AutoMirrored.Outlined.ManageSearch,
                    contentDescription = "Pokedex",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            label = R.string.pokedex_title,
            color = MaterialTheme.colorScheme.primary,
            route = Routes.PokemonList,
        ),
        BottomAppBarItem(
            icon = {
                Icon(
                    if (currentRoute == Routes.Team) {
                        Icons.Outlined.PeopleOutline
                    } else {
                        Icons.Filled.People
                    },
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Team",
                )
            },
            label = R.string.team_title,
            color = MaterialTheme.colorScheme.primary,
            route = Routes.Team,
        ),
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        )
    ) {
        BottomAppBar(
            modifier = modifier,
            bottomAppBarItems = bottomAppBarItems,
            currentRoute = currentRoute,
            onRouteSelected = { newRoute -> onRouteSelected(newRoute) },
        )
    }
}