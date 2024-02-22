package com.example.androidbasetemplate.ui.pokemondetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.PokemonDetails
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonListViewModel: PokemonDetailViewModel,
    navController: NavHostController,
    drawerState: DrawerState,
    selectedPokemonID: Int,
) {
    val currentPokemon by pokemonListViewModel.pokemon.observeAsState()
    pokemonListViewModel.getPokemonDetail(selectedPokemonID)

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                drawerState = drawerState,
                title = R.string.pokemon_detail_title,
            ) {
                navController.popBackStack()
            }
        },
        content = {
            Column(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                currentPokemon?.let { pokemonDetails ->
                    PokemonDescription(pokemonDetails)
                } ?: run {
                    FullScreenLoading()
                }
            }
        },
    )
}

@Composable
@Preview("Pokemon Description", uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PokemonDescription(
    pokemonDetail: PokemonDetails = PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
    ),
) {
    Card(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
    ) {
        Row(
            modifier = Modifier.fillMaxHeight().background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                modifier = Modifier.padding(8.dp).wrapContentHeight().align(Alignment.Top),
                shape = RoundedCornerShape(100.dp),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(pokemonDetail.sprites)
                        .crossfade(true).build(),
                    contentDescription = "PokemonResponse Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape).size(128.dp).padding(0.dp),
                )
            }
            Column(Modifier.fillMaxWidth().align(Alignment.Top)) {
                Text(
                    text = pokemonDetail.name.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp, 10.dp),
                )
            }
        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
    ) {
        CircularProgressIndicator()
    }
}
