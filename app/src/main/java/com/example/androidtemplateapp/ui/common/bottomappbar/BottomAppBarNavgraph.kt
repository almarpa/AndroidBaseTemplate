package com.example.androidtemplateapp.ui.common.bottomappbar

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.common.snackbar.SnackbarViewModel
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
    currentRoute: Routes,
    navigationActions: NavigationActions,
) {
    composable<Routes.PokemonList> {
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
    composable<Routes.Team> {
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
    composable<Pokemon> { navBackStackEntry ->
        val pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
        val teamViewModel: TeamViewModel = hiltViewModel()
        val settingsViewModel: SettingsViewModel = hiltViewModel()
        val snackbarViewModel: SnackbarViewModel = hiltViewModel()

        val pokemon = navBackStackEntry.toRoute<Pokemon>()
        val pokemonDetails by pokemonDetailsViewModel.pokemonDetails.collectAsStateWithLifecycle()
        val userAppTheme by settingsViewModel.userTheme.collectAsStateWithLifecycle()
        val messageIds by snackbarViewModel.messageIds.collectAsStateWithLifecycle()

        PokemonDetailsScreen(
            animatedVisibilityScope = this,
            pokemon = pokemon,
            pokemonDetails = pokemonDetails,
            messageIds = messageIds,
            userAppTheme = userAppTheme,
            onAddTeamMember = { pokemonSelected, isAddedToTeam ->
                teamViewModel.addPokemonToTeam(
                    pokemon = pokemonSelected,
                    isAdded = isAddedToTeam
                )
                if (isAddedToTeam) snackbarViewModel.showUserMessage(R.string.pokemon_added_to_team)
            },
            onBackPressed = { navigationActions.navigateBack() },
            onSnackBarDismissed = { snackbarViewModel.clearMessages() }
        )
    }
}