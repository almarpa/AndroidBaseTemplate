package com.example.androidtemplateapp.ui.common.bottomappbar

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.androidtemplateapp.entity.Pokemon.Companion.getPokemonFromJson
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.pokemondetails.PokemonDetailsScreen
import com.example.androidtemplateapp.ui.pokemondetails.PokemonDetailsViewModel
import com.example.androidtemplateapp.ui.pokemonlist.PokemonListScreen
import com.example.androidtemplateapp.ui.pokemonlist.PokemonListViewModel
import com.example.androidtemplateapp.ui.pokemonlist.SearchUiState
import com.example.androidtemplateapp.ui.settings.SettingsViewModel
import com.example.androidtemplateapp.ui.team.TeamScreen
import com.example.androidtemplateapp.ui.team.TeamUiState
import com.example.androidtemplateapp.ui.team.TeamViewModel

context(SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.bottomAppBarNavGraph(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    composable(Routes.PokemonList.route) {
        val pokemonListViewModel: PokemonListViewModel = hiltViewModel()
        val paginatedPokemonList = pokemonListViewModel.pokemonList.collectAsLazyPagingItems()
        val searchUiState: SearchUiState by pokemonListViewModel.uiState.collectAsStateWithLifecycle()

        PokemonListScreen(
            animatedVisibilityScope = this,
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
            searchUiState = searchUiState,
            paginatedPokemonList = paginatedPokemonList,
            onReload = { paginatedPokemonList.refresh() },
            onSearch = { text -> pokemonListViewModel.onPokemonSearch(text) },
            onDismissSearch = { pokemonListViewModel.removeCurrentSearch() },
        )
    }
    composable(Routes.Team.route) {
        val teamViewModel: TeamViewModel = hiltViewModel()
        val uiState: TeamUiState by teamViewModel.uiState.collectAsStateWithLifecycle()

        TeamScreen(
            drawerState = drawerState,
            currentRoute = currentRoute,
            navigationActions = navigationActions,
            uiState = uiState,
            onRetry = { teamViewModel.getTeamList() },
            onSave = { pokemon -> teamViewModel.createPokemonMemberAndReload(pokemon) }
        )
    }
    composable(
        route = Routes.PokemonDetail.route,
        arguments = listOf(
            navArgument("pokemon") {
                type = NavType.StringType
                nullable = false
            },
        ),
    ) { navBackStackEntry ->
        // TODO: use typed safe navigation (PokemonNavType)
        navBackStackEntry.arguments?.getString("pokemon")?.let { pokemon ->
            val pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
            val teamViewModel: TeamViewModel = hiltViewModel()
            val settingsViewModel: SettingsViewModel = hiltViewModel()

            val pokemonDetails by pokemonDetailsViewModel.pokemonDetails.collectAsStateWithLifecycle()
            val userAppTheme by settingsViewModel.userTheme.collectAsStateWithLifecycle()

            PokemonDetailsScreen(
                animatedVisibilityScope = this,
                pokemon = pokemon.getPokemonFromJson(),
                pokemonDetails = pokemonDetails,
                userAppTheme = userAppTheme,
                onAddTeamMember = { pokemonSelected, isAddedToTeam ->
                    teamViewModel.addPokemonToTeam(
                        pokemon = pokemonSelected,
                        isAdded = isAddedToTeam
                    )
                },
                onBackPressed = { navigationActions.navigateBack() },
            )
        }
    }
}