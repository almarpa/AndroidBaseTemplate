package com.example.androidtemplateapp.ui.common.bottomappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes

@Composable
fun AnimatedBottomAppBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    drawerState: DrawerState,
    currentRoute: Routes,
    navigationActions: NavigationActions,
) {
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
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions
        )
    }
}