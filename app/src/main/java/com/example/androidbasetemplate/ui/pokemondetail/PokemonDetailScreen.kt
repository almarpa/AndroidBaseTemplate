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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.PokemonDetail
import com.example.androidbasetemplate.ui.TemplateNavigationActions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemonListViewModel: PokemonDetailViewModel,
    drawerState: DrawerState,
    navigationActions: TemplateNavigationActions,
    selectedPokemonID: Int,
) {
    val currentPokemon by pokemonListViewModel.pokemon.observeAsState()
    pokemonListViewModel.getPokemonDetail(selectedPokemonID)

    Scaffold(
        topBar = { TopAppBar(drawerState, navigationActions) },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                currentPokemon?.let { pokemonDetail ->
                    PokemonDescription(pokemonDetail)
                } ?: run {
                    FullScreenLoading()
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview("Top App Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun TopAppBar(
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    navigationActions: TemplateNavigationActions? = null,
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(id = R.string.pokemon_detail_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                Icon(
                    painter = painterResource(R.drawable.ic_menu),
                    contentDescription = stringResource(R.string.menu_drawer_btn),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
    )
}

@Composable
@Preview("Pokemon Description", uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PokemonDescription(
    pokemonDetail: PokemonDetail = PokemonDetail(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
        types = listOf(),
        sprites = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    ),
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(modifier = Modifier.padding(8.dp).wrapContentHeight().align(Alignment.Top), shape = RoundedCornerShape(100.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemonDetail.sprites)
                        .crossfade(true)
                        .build(),
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
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
    ) {
        CircularProgressIndicator()
    }
}
