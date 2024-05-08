package com.example.androidbasetemplate.ui.pokemonlist.list

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.common.utils.getDominantColorFromDrawable
import com.example.androidbasetemplate.common.utils.getModifierWithSharedElementAnimationOrDefault
import com.example.androidbasetemplate.entity.Pokemon

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview("Pokemon Item View", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: Pokemon = Pokemon(
        id = 1,
        url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
        name = "Pokemon name",
    ),
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    onPokemonItemClick: (Pair<Int, Color>) -> Unit = { },
) {
    var dominantColor by remember {
        mutableStateOf(Color.White)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onPokemonItemClick(pokemon.id to dominantColor)
            },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = dominantColor),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(100.dp)
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "PokemonResponse Image",
                    contentScale = ContentScale.FillBounds,
                    loading = { CircularProgressIndicator() },
                    onSuccess = { success ->
                        getDominantColorFromDrawable(success.result.drawable) {
                            dominantColor = it
                        }
                    },
                    modifier = Modifier
                        .background(Color.White)
                        .clip(CircleShape)
                        .fillMaxWidth(.5f)
                        .aspectRatio(1f)
                        .padding(8.dp)
                        .then(
                            Modifier.getModifierWithSharedElementAnimationOrDefault(
                                modifier = Modifier,
                                sharedTransitionScope,
                                animatedContentScope,
                                pokemon.id,
                            )
                        ),
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = pokemon.name.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight(1000),
                    modifier = Modifier.padding(
                        5.dp,
                        10.dp
                    ),
                )
            }
        }
    }
}
