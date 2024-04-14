package com.example.androidbasetemplate.ui.pokemonlist.detail

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.PokemonDetails
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Pokemon Details Screen", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    pokemonDetails: PokemonDetails = PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
        imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
    ),
    navigateBack: () -> Unit = {},
) {
    BackHandler {
        navigateBack()
    }

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                drawerState = drawerState,
                title = R.string.pokemon_detail_title,
            ) {
                navigateBack()
            }
        },
        content = {
            Column(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PokemonDescription(modifier, pokemonDetails)
            }
        },
    )
}

@Composable
@Preview("Pokemon Description", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PokemonDescription(
    modifier: Modifier = Modifier,
    pokemonDetails: PokemonDetails = PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
        imageURL = ""
    ),
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemonDetails.imageURL)
                    .crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
            )
            Text(
                text = pokemonDetails.name.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(10.dp, 10.dp),
            )
        }
    }
}
