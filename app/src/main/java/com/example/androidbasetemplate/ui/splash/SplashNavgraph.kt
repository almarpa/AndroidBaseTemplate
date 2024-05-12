package com.example.androidbasetemplate.ui.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.TemplateDestinations

fun NavGraphBuilder.splashNavGraph(navigationActions: NavigationActions) {
    composable(TemplateDestinations.SPLASH_ROUTE) {
        SplashScreen { navigationActions.navigateToPokemonList() }
    }
}
