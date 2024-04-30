package com.example.androidbasetemplate.ui.pokemonlist

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.common.utils.getModifierWithSharedElementAnimationOrDefault
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.error.GenericRetryView
import com.example.androidbasetemplate.ui.common.loader.FullScreenLoader
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar
import com.example.androidbasetemplate.ui.pokemonlist.details.PokemonDetailsScreen
import com.example.androidbasetemplate.ui.pokemonlist.list.PokemonList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonListScreen(
    pokemonListViewModel: PokemonListViewModel,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
) {
    var animState: PokemonListScreenState by remember { mutableStateOf(PokemonListScreenState.List) }
    val uiState by pokemonListViewModel.uiState.collectAsStateWithLifecycle()

    SharedTransitionLayout(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            animState,
            label = "ListToDetailAnimation",
            contentKey = { it.javaClass },
            transitionSpec = { getTransitionSpec(animState) },
        ) { screenState ->
            when (screenState) {
                PokemonListScreenState.List -> {
                    Scaffold(
                        topBar = {
                            DrawerTopAppBar(
                                drawerState = drawerState,
                                navigationActions = navigationActions,
                                title = R.string.pokemon_list_title
                            )
                        },
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(it)
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
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
                                            sharedTransitionScope = this@SharedTransitionLayout,
                                            animatedContentScope = this@AnimatedContent,
                                            pokemonList = (uiState as PokemonListUiState.Success).pokemonList,
                                            pokemonListViewModel = pokemonListViewModel,
                                            navigationActions = navigationActions,
                                        ) { onPokemonItemClick ->
                                            animState =
                                                PokemonListScreenState.Details(onPokemonItemClick)
                                        }
                                    }
                                }
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

                is PokemonListScreenState.Details -> {
                    LaunchedEffect(key1 = true) {
                        pokemonListViewModel.getPokemonDetail(screenState.pokemonDetails.first)
                    }

                    val currentPokemon by pokemonListViewModel.pokemon.observeAsState()
                    currentPokemon?.let { pokemonDetails ->
                        PokemonDetailsScreen(
                            modifier = Modifier.getModifierWithSharedElementAnimationOrDefault(
                                modifier = Modifier,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@AnimatedContent,
                                elementPosition = screenState.pokemonDetails.first,
                            ),
                            pokemonDetails = pokemonDetails,
                            dominantColor = screenState.pokemonDetails.second,
                        ) {
                            animState = PokemonListScreenState.List
                        }
                    }
                }
            }
        }
    }
}

fun getTransitionSpec(animState: PokemonListScreenState) =
    if (animState == PokemonListScreenState.List) {
        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
    } else {
        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
    }
