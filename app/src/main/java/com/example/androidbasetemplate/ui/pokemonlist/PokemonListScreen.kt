package com.example.androidbasetemplate.ui.pokemonlist

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.error.GenericRetryView
import com.example.androidbasetemplate.ui.common.loader.FullScreenLoader
import com.example.androidbasetemplate.ui.common.mocks.getPokemonListMock
import com.example.androidbasetemplate.ui.common.mocks.getPokemonListViewModelMock
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.navigation.Routes
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar
import com.example.androidbasetemplate.ui.pokemonlist.list.PokemonList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel(),
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    Scaffold(
        topBar = {
            DrawerTopAppBar(
                drawerState = drawerState,
                navigationActions = navigationActions,
                title = R.string.pokemon_list_title
            )
        },
        content = { paddingValues ->
            val uiState by pokemonListViewModel.uiState.collectAsStateWithLifecycle(initialValue = PokemonListUiState.Loading)
            PokemonListContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                pokemonListViewModel = pokemonListViewModel,
                animatedVisibilityScope = animatedVisibilityScope,
                uiState = uiState,
            ) { pokemon ->
                navigationActions.navigateToDetailNavGraph(pokemon)
            }
        },
        bottomBar = {
            TemplateBottomAppBar(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navigationActions
            )
        },
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonListContent(
    modifier: Modifier = Modifier,
    pokemonListViewModel: PokemonListViewModel,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: PokemonListUiState,
    navigateToPokemonDetail: (Pokemon) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            is PokemonListUiState.Loading -> {
                FullScreenLoader()
            }

            is PokemonListUiState.Error -> {
                GenericRetryView { pokemonListViewModel.getPokemonList() }
            }

            is PokemonListUiState.Success -> {
                PokemonList(
                    animatedVisibilityScope = animatedVisibilityScope,
                    pokemonListViewModel = pokemonListViewModel,
                    pokemonList = uiState.pokemonList,
                ) { onPokemonItemClick ->
                    navigateToPokemonDetail(onPokemonItemClick)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview("Pokemon List Screen")
fun PokemonListScreenPreview() {
    TemplatePreviewTheme {
        PokemonListScreen(
            animatedVisibilityScope = it,
            pokemonListViewModel = getPokemonListViewModelMock(),
            drawerState = DrawerState(DrawerValue.Closed),
            Routes.PokemonList.route,
            NavigationActions(rememberNavController())
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon List Content")
fun PokemonListContentPreview() {
    TemplatePreviewTheme {
        PokemonListContent(
            modifier = Modifier,
            animatedVisibilityScope = it,
            pokemonListViewModel = getPokemonListViewModelMock(),
            uiState = PokemonListUiState.Success(getPokemonListMock())
        )
    }
}