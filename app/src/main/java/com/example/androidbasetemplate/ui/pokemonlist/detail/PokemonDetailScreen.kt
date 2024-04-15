package com.example.androidbasetemplate.ui.pokemonlist.detail

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.entity.PokemonDetails
import java.util.Locale

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
    imageHeight: Int = 300,
    navigateBack: () -> Unit = {},
) {
    BackHandler {
        navigateBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.Blue)
    ) {
        PokemonDetailAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
        ) {
            navigateBack()
        }

        PokemonDescription(
            pokemon = pokemonDetails,
            imageHeight= imageHeight,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = imageHeight.dp / 2 - 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        )

        PokemonImageAnimation(pokemonDetails.imageURL, imageHeight, modifier)
    }
}

@Composable
fun PokemonImageAnimation(pokemonImage: String, imageHeight: Int, modifier: Modifier) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemonImage)
                .crossfade(true)
                .build(),
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(imageHeight.dp)
                .then(modifier)
        )
    }
}

@Preview("Pokemon Details Screen Top Bar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonDetailAppBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    onBackPressed()
                }
        )
    }
}

@Preview("Pokemon Description", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonDescription(
    modifier: Modifier = Modifier,
    pokemon: PokemonDetails,
    imageHeight: Int = 250,
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = imageHeight.dp / 2)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${pokemon.id} ${pokemon.name.uppercase(Locale.ROOT)}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        /*PokemonTypeSection(types = pokemonInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height
        )
        PokemonBaseStats(pokemonInfo = pokemonInfo)*/
    }
}
