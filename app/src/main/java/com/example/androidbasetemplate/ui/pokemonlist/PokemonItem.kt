package com.example.androidbasetemplate.ui.pokemonlist

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
@Preview("Pokemon Item", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PokemonItem(
    name: String = "Pokemon name",
    url: String? = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(true) { onClick.invoke() }
            .focusable(true),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(modifier = Modifier.padding(8.dp), shape = RoundedCornerShape(100.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "PokemonResponse Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape).size(128.dp).padding(0.dp),
                )
            }
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = name.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp, 10.dp),
                )
            }
        }
    }
}
