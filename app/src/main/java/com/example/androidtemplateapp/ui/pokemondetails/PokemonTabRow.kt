package com.example.androidtemplateapp.ui.pokemondetails

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplateapp.R
import com.example.androidtemplateapp.entity.PokemonDetails
import com.example.androidtemplateapp.ui.common.mocks.getPokemonDetailsMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import com.example.androidtemplateapp.ui.common.tabrow.SwipeableTabRow

@Composable
fun PokemonTabRow(modifier: Modifier = Modifier, pokemonDetails: PokemonDetails) {
    val tabs =
        listOf(
            LocalContext.current.getString(R.string.stats),
            LocalContext.current.getString(R.string.moves),
        )

    SwipeableTabRow(
        modifier = modifier,
        tabs = tabs,
        contentScreens = listOf(
            { PokemonStats(stats = pokemonDetails.stats) },
            { PokemonMoves(moves = pokemonDetails.moves) },
        ),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        indicatorColor = Color.DarkGray
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(name = "Pokemon Tab Row", showBackground = true)
@Preview(
    name = "Dark Pokemon Tab Row",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun PokemonTabRowPreview() {
    TemplatePreviewTheme {
        PokemonTabRow(pokemonDetails = getPokemonDetailsMock())
    }
}