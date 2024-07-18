package com.example.androidtemplateapp.ui.pokemonlist.common

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.common.utils.getDominantColorFromDrawable
import com.example.androidtemplateapp.common.utils.pokemonSharedElement
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import java.net.URLDecoder

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PokemonItem(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    pokemon: Pokemon,
    onPokemonItemClick: (Pokemon) -> Unit = { },
) {
    val primaryColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember { mutableStateOf(primaryColor) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onPokemonItemClick(pokemon) },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = dominantColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(URLDecoder.decode(pokemon.url, "UTF-8"))
                    .crossfade(true)
                    .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
                    .build(),
                contentDescription = "Pokemon Image",
                contentScale = ContentScale.FillBounds,
                onSuccess = { success ->
                    getDominantColorFromDrawable(success.result.drawable) {
                        dominantColor = it
                        pokemon.dominantColor = it.toArgb()
                    }
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxWidth(.8f)
                    .aspectRatio(1f)
                    .padding(top = 8.dp)
                    .pokemonSharedElement(
                        isLocalInspectionMode = LocalInspectionMode.current,
                        state = rememberSharedContentState(key = "item-image${pokemon.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )

            Text(
                text = pokemon.name.uppercase(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview("Pokemon Item View")
fun PokemonItemPreview() {
    TemplatePreviewTheme {
        PokemonItem(
            animatedVisibilityScope = it,
            pokemon = Pokemon(
                id = 1,
                url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
                name = "Pokemon name",
                dominantColor = null,
                isTeamMember = false
            )
        )
    }
}
