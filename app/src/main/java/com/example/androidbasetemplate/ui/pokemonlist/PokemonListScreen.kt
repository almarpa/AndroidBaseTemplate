package com.example.androidbasetemplate.ui.pokemonlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.NavigationActions
import com.example.androidbasetemplate.ui.common.bottomappbar.TemplateBottomAppBar
import com.example.androidbasetemplate.ui.common.topappbar.DrawerTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    pokemonListViewModel: PokemonListViewModel,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    onNavigateToPokemonDetail: (Int) -> Unit,
) {
    val uiState by pokemonListViewModel.uiState.collectAsStateWithLifecycle()

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
                            (uiState as PokemonListUiState.Success).pokemonList,
                            navigationActions,
                        ) { selectedPokemonID ->
                            onNavigateToPokemonDetail(selectedPokemonID)
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
        }
    )
}

@Composable
@Preview("Pokemon List", uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PokemonList(
    pokemonList: List<Pokemon> = listOf(
        Pokemon(
            url = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            name = "This is an example of product description 1",
        ),
        Pokemon(
            url = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            name = "This is an example of product description 2",
        ),
        Pokemon(
            url = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            name = "This is an example of product description 3",
        ),
    ),
    navigationActions: NavigationActions? = null,
    onNavigateToPokemonDetail: (Int) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(pokemonList) { pokemon ->
            PokemonItem(url = pokemon.url, name = pokemon.name) { selectedPokemonID ->
                onNavigateToPokemonDetail(selectedPokemonID)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

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
