package com.example.androidbasetemplate.ui.pokemonlist.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbasetemplate.R
import kotlin.math.round

@Preview("Pokemon Measures", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonMeasures(
    pokemonWeight: Int = 100,
    pokemonHeight: Int = 100,
) {
    val pokemonWeightInKg = remember { round(pokemonWeight * 100f) / 1000f }
    val pokemonHeightInMeters = remember { round(pokemonHeight * 100f) / 1000f }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PokemonMeasureItem(
            modifier = Modifier.weight(1f),
            value = pokemonWeightInKg,
            unit = "kg",
            icon = painterResource(id = R.drawable.ic_weight),
        )
        Spacer(
            modifier = Modifier
                .height(60.dp)
                .size(1.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        PokemonMeasureItem(
            modifier = Modifier.weight(1f),
            value = pokemonHeightInMeters,
            unit = "m",
            icon = painterResource(id = R.drawable.ic_height),
        )
    }
}

@Preview("Pokemon Measure", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonMeasureItem(
    modifier: Modifier = Modifier,
    value: Float = 2f,
    unit: String = "Units",
    icon: Painter = painterResource(id = R.drawable.ic_weight),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(4.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$value $unit",
            color = MaterialTheme.colorScheme.primary
        )
    }
}