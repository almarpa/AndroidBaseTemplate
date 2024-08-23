package com.example.androidtemplateapp.ui.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes

fun NavGraphBuilder.splashNavGraph(navigationActions: NavigationActions) {
    composable<Routes.Splash> {
        SplashScreen { navigationActions.navigateToPokemonList() }
    }
}
