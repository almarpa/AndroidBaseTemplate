package com.example.androidbasetemplate.ui.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes

fun NavGraphBuilder.splashNavGraph(navigationActions: NavigationActions) {
    composable(Routes.Splash.route) {
        SplashScreen { navigationActions.navigateToPokemonList() }
    }
}
