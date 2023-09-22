package com.example.androidbasetemplate.ui.pokemonlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R

@Composable
@Preview("Pokemon Item", uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PokemonItem(
    name: String = "Pokemon name",
    url: String? = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_blur),
                contentDescription = "PokemonResponse Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).size(128.dp).padding(0.dp),
            )
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp, 10.dp),
                )
            }
        }
    }
}
