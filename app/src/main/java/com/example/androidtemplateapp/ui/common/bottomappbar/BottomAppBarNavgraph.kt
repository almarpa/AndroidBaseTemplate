package com.example.androidtemplateapp.ui.common.bottomappbar

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import androidx.paging.compose.collectAsLazyPagingItems
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

@OptIn(ExperimentalSharedTransitionApi::class)
fun <T : Any> EntryProviderBuilder<T>.bottomAppBarNavGraph(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    drawerState: DrawerState,
    currentRoute: Routes,
    navigationActions: NavigationActions,
) {
    entry<Routes.PokemonList> {
        val pokemonListViewModel: PokemonListViewModel = hiltViewModel()
        val paginatedPokemonList = pokemonListViewModel.pokemonList.collectAsLazyPagingItems()
        val searchUiState: SearchUiState by pokemonListViewModel.searchUiState.collectAsStateWithLifecycle()

        sharedTransitionScope.PokemonListScreen(
            animatedVisibilityScope = animatedVisibilityScope,
            drawerState = drawerState,
            currentRoute = currentRoute,
            searchUiState = searchUiState,
            paginatedPokemonList = paginatedPokemonList,
            onItemSelected = { item -> navigationActions.navigateToDetail(item) },
            onRouteSelected = { route ->
                if (route == Routes.Team) navigationActions.navigateToTeamList() else Unit
            },
            onReload = { paginatedPokemonList.refresh() },
            onSearch = { text -> pokemonListViewModel.onPokemonSearch(text) },
            onDismissSearch = { pokemonListViewModel.removeCurrentSearch() },
            onPokemonImageLoaded = { pokemonId, color ->
                pokemonListViewModel.addPokemonDominantColor(pokemonId, color)
            },
        )
    }
    entry<Routes.Team> {
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
    entry<Routes.Detail> { route ->
        val pokemonDetailsViewModel: PokemonDetailsViewModel = hiltViewModel()
        val teamViewModel: TeamViewModel = hiltViewModel()
        val settingsViewModel: SettingsViewModel = hiltViewModel()

        val pokemonDetailsUiState by pokemonDetailsViewModel.detailsUiState.collectAsStateWithLifecycle()
        val userAppTheme by settingsViewModel.userData.collectAsStateWithLifecycle()

        sharedTransitionScope.PokemonDetailsScreen(
            animatedVisibilityScope = animatedVisibilityScope,
            pokemon = route.pokemon,
            pokemonDetailsUiState = pokemonDetailsUiState,
            userAppTheme = userAppTheme.theme,
            onFetchDetails = { pokemonDetailsViewModel.getPokemonDetails(route.pokemon.id) },
            onAddTeamMember = { pokemon, added -> teamViewModel.addPokemonToTeam(pokemon, added) },
            onBackPressed = { navigationActions.navigateBack() },
        )
    }
}