package com.example.androidbasetemplate.ui.pokemonlist.details

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.PokemonDetails
import com.example.androidbasetemplate.entity.Stat
import com.example.androidbasetemplate.entity.StatX
import com.example.androidbasetemplate.entity.TypeX
import com.example.androidbasetemplate.entity.TypeXX
import com.example.androidbasetemplate.entity.enum.PokemonTypeEnum
import com.example.androidbasetemplate.entity.enum.StatNameEnum
import com.example.androidbasetemplate.ui.common.topappbar.DefaultTopAppBar
import java.util.Locale

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier,
    pokemonDetails: PokemonDetails,
    dominantColor: Color,
    imageSize: Int = 300,
    navigateBack: () -> Unit,
) {
    BackHandler { navigateBack() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getBackgroundColor(dominantColor)),
    ) {
        PokemonDetailTopAppBar { navigateBack() }
        PokemonCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = imageSize.dp / 2 - 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 30.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            imageSize = imageSize,
            pokemonDetails = pokemonDetails,
        )
        PokemonImageAnimation(modifier, pokemonDetails.imageURL, imageSize)
    }
}

@Composable
fun getBackgroundColor(dominantColor: Color): Brush {
    return if (isSystemInDarkTheme()) {
        Brush.verticalGradient(listOf(Color.Black, dominantColor), startY = 0.0f, endY = 400.0f)
    } else {
        Brush.verticalGradient(listOf(Color.White, dominantColor), startY = 0.0f, endY = 400.0f)
    }
}

@Preview("Pokemon Details TopAppBar", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonDetailTopAppBar(navigateBack: () -> Unit = {}) {
    DefaultTopAppBar(
        modifier = Modifier,
        title = R.string.empty_string
    ) {
        navigateBack()
    }
}

@Preview("Pokemon Image Animation", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonImageAnimation(
    modifier: Modifier = Modifier,
    pokemonImageUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
    imageSize: Int = 300,
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.wrapContentHeight()
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemonImageUrl)
                .crossfade(true)
                .build(),
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(imageSize.dp)
                .then(modifier)
        )
    }
}

@Preview("Pokemon Description", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    imageSize: Int = 300,
    pokemonDetails: PokemonDetails = PokemonDetails(
        id = 1,
        order = 1,
        name = "Bulbasour",
        baseExperience = 64,
        height = 24,
        weight = 12,
        imageURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        stats = listOf(
            Stat(
                baseStat = 50, effort = 30, statX = StatX(StatNameEnum.ATTACK, "")
            ), Stat(
                baseStat = 80, effort = 70, statX = StatX(StatNameEnum.DEFENSE, "")
            )
        ),
        types = listOf(
            TypeX(
                slot = 1, typeXX = TypeXX(PokemonTypeEnum.BUG, "")
            ), TypeX(
                slot = 2, typeXX = TypeXX(PokemonTypeEnum.POISON, "")
            )
        )
    ),
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .padding(top = imageSize.dp / 2)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "${pokemonDetails.id} ${pokemonDetails.name.uppercase(Locale.ROOT)}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        PokemonType(pokemonDetails.types)
        PokemonMeasures(pokemonDetails.weight, pokemonDetails.height)
        PokemonStats(pokemonDetails)
    }
}

