package com.example.androidtemplateapp.ui.splash

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes

fun <T : Any> EntryProviderBuilder<T>.splashNavGraph(navigationActions: NavigationActions) {
    entry<Routes.Splash> {
        SplashScreen { navigationActions.navigateToPokemonList() }
    }
}
