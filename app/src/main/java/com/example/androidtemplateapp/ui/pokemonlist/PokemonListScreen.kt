package com.example.androidtemplateapp.ui.pokemonlist

import android.content.res.Configuration
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.bottomappbar.BottomAppBar
import com.example.androidtemplateapp.ui.common.error.GenericRetryView
import com.example.androidtemplateapp.ui.common.loader.FullScreenLoader
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.common.notfound.NotFoundView
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.topappbar.DrawerTopAppBar
import com.example.androidtemplateapp.ui.pokemonlist.list.PokemonList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    uiState: PokemonListUiState,
    onGetPokemonList: () -> Unit,
    textSearched: String,
    onSearch: (text: String) -> Unit,
    onDismissSearch: () -> Unit,
    visibleItems: Pair<Int, Int>,
    onDisposeItems: (Pair<Int, Int>) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DrawerTopAppBar(
                drawerState = drawerState,
                title = R.string.pokedex_title,
                scrollBehavior = scrollBehavior,
                allowSearch = true,
                textSearched = textSearched,
                onDismissSearch = { onDismissSearch() },
                onSearch = { text -> onSearch(text) }
            )
        },
        content = { paddingValues ->
            PokemonListContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                animatedVisibilityScope = animatedVisibilityScope,
                uiState = uiState,
                getPokemonList = { onGetPokemonList() },
                visibleItems = visibleItems,
                onDisposeItems = onDisposeItems,
                onNavigateToPokemonDetail = { navigationActions.navigateToDetailNavGraph(it) }
            )
        },
        bottomBar = {
            BottomAppBar(
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
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: PokemonListUiState,
    getPokemonList: () -> Unit,
    visibleItems: Pair<Int, Int>,
    onDisposeItems: (Pair<Int, Int>) -> Unit,
    onNavigateToPokemonDetail: (Pokemon) -> Unit,
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
                GenericRetryView { getPokemonList() }
            }

            is PokemonListUiState.Success -> {
                PokemonList(
                    animatedVisibilityScope = animatedVisibilityScope,
                    visibleItems = visibleItems,
                    onDisposeItems = { onDisposeItems(it) },
                    pokemonList = uiState.pokemonList,
                    onPokemonItemClick = { onNavigateToPokemonDetail(it) }
                )
            }

            is PokemonListUiState.NotFound -> {
                NotFoundView()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon List Screen")
@Preview("Pokemon List Screen", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PokemonListScreenPreview() {
    TemplatePreviewTheme {
        PokemonListScreen(
            animatedVisibilityScope = it,
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.PokemonList.route,
            navigationActions = NavigationActions(rememberNavController()),
            uiState = PokemonListUiState.Success(getPokemonListMock()),
            onGetPokemonList = {},
            textSearched = "",
            onDismissSearch = {},
            onSearch = {},
            visibleItems = 0 to 0,
            onDisposeItems = {}
        )
    }
}