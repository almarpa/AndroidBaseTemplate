package com.example.androidtemplateapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.androidtemplateapp.ui.common.bottomappbar.bottomAppBarNavGraph
import com.example.androidtemplateapp.ui.common.drawer.drawerNavGraph
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.splash.splashNavGraph

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TemplateNavHost(
    navBackStack: NavBackStack,
    drawerState: DrawerState,
    currentRoute: Routes,
    navigationActions: NavigationActions,
) {
    SharedTransitionLayout {
        NavDisplay(
            backStack = navBackStack,
            onBack = { navigationActions.navigateBack },
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
            ),
            transitionSpec = {
                slideInHorizontally(animationSpec = tween(300)) + fadeIn() togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(300)
                        ) + fadeOut()
            },
            popTransitionSpec = {
                slideInHorizontally(animationSpec = tween(300)) + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(300)) + fadeOut()
            },
            entryProvider = entryProvider {
                AnimatedVisibility(visible = true) {
                    splashNavGraph(navigationActions)
                    bottomAppBarNavGraph(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility,
                        drawerState = drawerState,
                        currentRoute = currentRoute,
                        navigationActions = navigationActions
                    )
                    drawerNavGraph(navigationActions)
                }
            },
        )
    }
}
