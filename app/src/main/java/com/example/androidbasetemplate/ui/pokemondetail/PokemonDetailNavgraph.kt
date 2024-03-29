package com.example.androidbasetemplate.ui.pokemondetail

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidbasetemplate.MainActivity
import com.example.androidbasetemplate.ui.common.TemplateDestinations

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.pokemonDetailNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
) {
    composable(
        route = TemplateDestinations.POKEMON_DETAIL,
        arguments = listOf(navArgument("pokemonID") { type = NavType.IntType }),
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getInt("pokemonID")?.let { selectedPokemonID ->
            PokemonDetailScreen(
                pokemonListViewModel = ViewModelProvider(LocalContext.current as MainActivity)[PokemonDetailViewModel::class.java],
                navController = navController,
                drawerState = drawerState,
                selectedPokemonID = selectedPokemonID,
            )
        }
    }
}
