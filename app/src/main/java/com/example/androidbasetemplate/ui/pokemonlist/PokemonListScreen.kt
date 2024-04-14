package com.example.androidbasetemplate.ui.pokemonlist

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.common.utils.getModifierWithSharedElementAnimationOrDefault
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar
import com.example.androidbasetemplate.ui.pokemonlist.detail.PokemonDetailsScreen
import com.example.androidbasetemplate.ui.pokemonlist.interfaces.PokemonListScreenState

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
                                modifier = Modifier.padding(it),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                when (uiState) {
                                    is PokemonListUiState.Loading -> {
                                        FullScreenLoading()
                                    }

                                    is PokemonListUiState.Error -> {
                                        /*TODO: retry btn */
                                    }

                                    is PokemonListUiState.Success -> {
                                        PokemonList(
                                            sharedTransitionScope = this@SharedTransitionLayout,
                                            animatedContentScope = this@AnimatedContent,
                                            pokemonList = (uiState as PokemonListUiState.Success).pokemonList,
                                            pokemonListViewModel = pokemonListViewModel,
                                            navigationActions = navigationActions,
                                        ) { selectedPokemonID ->
                                            animState =
                                                PokemonListScreenState.Details(selectedPokemonID)
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
                    pokemonListViewModel.getPokemonDetail(screenState.item)
                    val currentPokemon by pokemonListViewModel.pokemon.observeAsState()

                    currentPokemon?.let { pokemonDetails ->
                        PokemonDetailsScreen(
                            Modifier.getModifierWithSharedElementAnimationOrDefault(
                                modifier = Modifier,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@AnimatedContent,
                                elementPosition = screenState.item,
                            ),
                            drawerState,
                            pokemonDetails
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

@Preview("Full Screen Loader", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    ) {
        CircularProgressIndicator()
    }
}
