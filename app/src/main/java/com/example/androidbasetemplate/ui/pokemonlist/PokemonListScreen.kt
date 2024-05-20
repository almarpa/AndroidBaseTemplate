package com.example.androidbasetemplate.ui.pokemonlist

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.error.GenericRetryView
import com.example.androidbasetemplate.ui.common.loader.FullScreenLoader
import com.example.androidbasetemplate.ui.common.navigation.NavigationActions
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar
import com.example.androidbasetemplate.ui.pokemonlist.list.PokemonList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
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
            PokemonListContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                pokemonListViewModel = hiltViewModel(),
                animatedVisibilityScope = animatedVisibilityScope,
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
    pokemonListViewModel: PokemonListViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToPokemonDetail: (Pokemon) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val uiState by pokemonListViewModel.uiState.collectAsStateWithLifecycle()
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
                    pokemonList = (uiState as PokemonListUiState.Success).pokemonList,
                ) { onPokemonItemClick ->
                    navigateToPokemonDetail(onPokemonItemClick)
                }
            }
        }
    }
}