package com.example.androidbasetemplate.ui.common.bottomappbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions

@Composable
fun AnimatedBottomAppBar(
    isVisible: Boolean,
    drawerState: DrawerState,
    currentRoute: String,
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
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions
        )
    }
}