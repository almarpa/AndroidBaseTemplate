package com.example.androidbasetemplate.ui.pokemonlist.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.mocks.getPokemonMock
import com.example.androidbasetemplate.ui.common.preview.TemplatePreviewTheme
import java.util.Locale

@Composable
fun PokemonName(pokemon: Pokemon, onFavouriteClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FavoriteButton(pokemon.isFavourite) { onFavouriteClick(it) }
        Text(
            modifier = Modifier.fillMaxWidth(.9f),
            text = "${pokemon.id} ${pokemon.name.uppercase(Locale.getDefault())}",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun FavoriteButton(isFavouriteYet: Boolean?, onFavouriteClick: (Boolean) -> Unit) {
    var isFavourite by remember { mutableStateOf(isFavouriteYet ?: false) }
    val favScale by animateFloatAsState(
        targetValue = if (isFavourite) 1.5f else 1f,
        label = "Favourite Button Scale"
    )
    IconButton(
        modifier = Modifier
            .fillMaxWidth(.1f)
            .padding(vertical = 4.dp)
            .scale(favScale),
        onClick = {
            isFavourite = !isFavourite
            onFavouriteClick(isFavourite)
        },
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = if (isFavourite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite icon",
            tint = if (isFavourite) Color.Red else MaterialTheme.colorScheme.primary,
        )
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview()
@Composable
fun PokemonNamePreview() {
    TemplatePreviewTheme {
        PokemonName(pokemon = getPokemonMock()) {}
    }
}