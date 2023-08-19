package com.example.androidbasetemplate.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [TemplateApp].
 */
object TemplateDestinations {
    const val HOME_ROUTE = "home"
    const val PRODUCT_LIST = "productList"
    const val PRODUCT_DETAIL = "productDetail"
}

/**
 * Models the navigation actions in the app.
 */
class TemplateNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(TemplateDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToProductList: () -> Unit = {
        navController.navigate(TemplateDestinations.PRODUCT_LIST) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToProductDetail: () -> Unit = {
        navController.navigate(TemplateDestinations.PRODUCT_DETAIL) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
