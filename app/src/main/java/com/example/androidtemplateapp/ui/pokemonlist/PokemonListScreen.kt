package com.example.androidtemplateapp.ui.pokemonlist

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.bottomappbar.AnimatedBottomAppBar
import com.example.androidtemplateapp.ui.common.error.GenericRetryView
import com.example.androidtemplateapp.ui.common.loader.FullScreenLoader
import com.example.androidtemplateapp.ui.common.mocks.getPokemonListMock
import com.example.androidtemplateapp.ui.common.navigation.Routes
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.pokemonlist.list.PokemonList
import com.example.androidtemplateapp.ui.pokemonlist.search.PokemonSearchTopAppBar
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.PokemonListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    drawerState: DrawerState,
    currentRoute: Routes,
    searchUiState: SearchUiState,
    paginatedPokemonList: LazyPagingItems<Pokemon>,
    onPokemonImageLoaded: (Int, Int) -> Unit,
    onItemSelected: (Pokemon) -> Unit,
    onRouteSelected: (Routes) -> Unit,
    onReload: () -> Unit,
    onSearch: (text: String) -> Unit,
    onDismissSearch: () -> Unit,
) {
    val activity = (LocalActivity.current)
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    var isBottomAppBarVisible by rememberSaveable { mutableStateOf(true) }
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()

    BackHandler { activity?.finish() }
    LaunchedEffect(Unit) { isBottomAppBarVisible = true }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            PokemonSearchTopAppBar(
                animatedVisibilityScope = animatedVisibilityScope,
                drawerState = drawerState,
                scrollBehaviour = scrollBehaviour,
                uiState = searchUiState,
                isSearchActive = isSearchActive,
                onSearchActiveChange = { isActive ->
                    isSearchActive = isActive
                    isBottomAppBarVisible = !isActive
                },
                onDismissSearch = { onDismissSearch() },
                onSearch = { onSearch(it) },
                onSelected = { onItemSelected(it) }
            )
        },
        content = { paddingValues ->
            PokemonListContent(
                modifier = Modifier.padding(paddingValues = paddingValues),
                animatedVisibilityScope = animatedVisibilityScope,
                paginatedPokemonList = paginatedPokemonList,
                onReload = { onReload() },
                onNavigateToPokemonDetail = { pokemon ->
                    isBottomAppBarVisible = false
                    onItemSelected(pokemon)
                },
                onPokemonImageLoaded = { id, color -> onPokemonImageLoaded(id, color) }
            )
        },
        bottomBar = {
            AnimatedBottomAppBar(
                modifier = Modifier.renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f),
                isVisible = isBottomAppBarVisible,
                currentRoute = currentRoute,
            ) { routeSelected ->
                coroutineScope.launch { drawerState.close() }
                onRouteSelected(routeSelected)
            }
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
    onPokemonImageLoaded: (Int, Int) -> Unit,
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
                        onPokemonItemClick = { onNavigateToPokemonDetail(it) },
                        onPokemonImageLoaded = { id, color -> onPokemonImageLoaded(id, color) },
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
            currentRoute = Routes.PokemonList,
            searchUiState = SearchUiState.Success(getPokemonListMock()),
            paginatedPokemonList = flowOf(PagingData.from(getPokemonListMock())).collectAsLazyPagingItems(),
            onReload = {},
            onDismissSearch = {},
            onSearch = {},
            onItemSelected = {},
            onRouteSelected = {},
            onPokemonImageLoaded = { _, _ -> }
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
            currentRoute = Routes.PokemonList,
            searchUiState = SearchUiState.Error,
            paginatedPokemonList = flowOf(PagingData.from(getPokemonListMock())).collectAsLazyPagingItems(),
            onReload = {},
            onDismissSearch = {},
            onSearch = {},
            onItemSelected = {},
            onRouteSelected = {},
            onPokemonImageLoaded = { _, _ -> }
        )
    }
}