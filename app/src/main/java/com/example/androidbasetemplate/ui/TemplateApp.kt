package com.example.androidbasetemplate.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.ui.theme.TemplateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateApp() {
    TemplateTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            TemplateNavigationActions(navController)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: TemplateDestinations.HOME_ROUTE

        ModalNavigationDrawer(
            drawerContent = {
                AppDrawer(
                    currentRoute = currentRoute,
                    navigateToHome = navigationActions.navigateToHome,
                    navigateToProductList = navigationActions.navigateToProductList,
                )
            },
            drawerState = DrawerState(DrawerValue.Closed),
        ) {
            Row {
                TemplateNavGraph(
                    navController = navController,
                )
            }
        }
    }
}
