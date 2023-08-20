package com.example.androidbasetemplate.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.MainActivity
import com.example.androidbasetemplate.ui.home.Home
import com.example.androidbasetemplate.ui.home.HomeViewModel
import com.example.androidbasetemplate.ui.productlist.ProductList
import com.example.androidbasetemplate.ui.productlist.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TemplateDestinations.HOME_ROUTE,
    drawerState: DrawerState,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(TemplateDestinations.HOME_ROUTE) {
            Home(
                homeViewModel = ViewModelProvider(LocalContext.current as MainActivity)[HomeViewModel::class.java],
                drawerState = drawerState,
            )
        }
        composable(TemplateDestinations.PRODUCT_LIST) {
            ProductList(
                productListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[ProductListViewModel::class.java],
                drawerState = drawerState,
            )
        }
        composable(TemplateDestinations.PRODUCT_DETAIL) { navBackStackEntry ->
            /* TODO: implement product detail */
            // val selectedProductID = navBackStackEntry.arguments?.getString(PRODUCT_ID)
        }
    }
}
