package com.example.androidtemplateapp.ui.pokemonlist

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.bottomappbar.AnimatedBottomAppBar
import com.example.androidtemplateapp.ui.common.error.GenericRetryView
import com.example.androidtemplateapp.ui.common.loader.FullScreenLoader
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.navigation.NavigationActions
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.pokemonlist.list.PokemonList
import com.example.androidtemplateapp.ui.pokemonlist.search.PokemonSearchTopAppBar
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class)
@Composable
fun SharedTransitionScope.PokemonListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    searchUiState: SearchUiState,
    paginatedPokemonList: LazyPagingItems<Pokemon>,
    onReload: () -> Unit,
    onSearch: (text: String) -> Unit,
    onDismissSearch: () -> Unit,
) {
    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            PokemonSearchTopAppBar(
                animatedVisibilityScope = animatedVisibilityScope,
                drawerState = drawerState,
                uiState = searchUiState,
                isSearchActive = isSearchActive,
                onSearchActiveChange = { isActive -> isSearchActive = isActive },
                onDismissSearch = { onDismissSearch() },
                onSearch = { onSearch(it) },
                onSelected = { navigationActions.navigateToDetailNavGraph(it) }
            )
        },
        content = { paddingValues ->
            PokemonListContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                animatedVisibilityScope = animatedVisibilityScope,
                paginatedPokemonList = paginatedPokemonList,
                onReload = { onReload() },
                onNavigateToPokemonDetail = { navigationActions.navigateToDetailNavGraph(it) }
            )
        },
        bottomBar = {
            AnimatedBottomAppBar(
                isVisible = !isSearchActive,
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationActions = navigationActions
            )
        },
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class)
@Composable
fun SharedTransitionScope.PokemonListContent(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    paginatedPokemonList: LazyPagingItems<Pokemon>,
    onReload: () -> Unit,
    onNavigateToPokemonDetail: (Pokemon) -> Unit,
) {
    Box {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = false,
            onRefresh = { onReload() }
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (paginatedPokemonList.loadState.refresh) {
                is LoadState.Loading -> {
                    FullScreenLoader()
                }

                is LoadState.Error -> {
                    GenericRetryView { onReload() }
                }

                is LoadState.NotLoading -> {
                    PokemonList(
                        animatedVisibilityScope = animatedVisibilityScope,
                        pokemonList = paginatedPokemonList,
                        onPokemonItemClick = { onNavigateToPokemonDetail(it) }
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
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
            searchUiState = SearchUiState.Success(getPokemonListMock()),
            paginatedPokemonList = flowOf(PagingData.from(getPokemonListMock())).collectAsLazyPagingItems(),
            onReload = {},
            onDismissSearch = {},
            onSearch = {},
        )
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon List Screen")
@Preview("Pokemon List Screen", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PokemonListScreenWithSearchActivePreview() {
    TemplatePreviewTheme {
        PokemonListScreen(
            animatedVisibilityScope = it,
            drawerState = DrawerState(DrawerValue.Closed),
            currentRoute = Routes.PokemonList.route,
            navigationActions = NavigationActions(rememberNavController()),
            searchUiState = SearchUiState.Success(getPokemonListMock()),
            paginatedPokemonList = flowOf(PagingData.from(getPokemonListMock())).collectAsLazyPagingItems(),
            onReload = {},
            onDismissSearch = {},
            onSearch = {},
        )
    }
}