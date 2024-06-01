package com.example.androidbasetemplate.ui.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.androidbasetemplate.R
import com.example.androidbasetemplate.entity.Pokemon
import com.example.androidbasetemplate.ui.common.spacer.CustomSpacer
import java.net.URLDecoder

@Composable
fun TeamList(paddingValues: PaddingValues, pokemonList: List<Pokemon>) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(pokemonList) { pokemon ->
            val dominantColorOrDefault =
                pokemon.dominantColor?.let { Color(it) } ?: MaterialTheme.colorScheme.primary
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (pokemonList.first() != pokemon) {
                    CustomSpacer(modifier = Modifier.background(dominantColorOrDefault), 30, 15)
                }

                MemberImage(pokemon, dominantColorOrDefault)
                CustomSpacer(modifier = Modifier.background(dominantColorOrDefault), 5, 15)
                MemberName(pokemon, dominantColorOrDefault)

                if (pokemonList.last() != pokemon) {
                    CustomSpacer(modifier = Modifier.background(dominantColorOrDefault), 30, 15)
                }
            }
        }
    }
}

@Composable
fun MemberImage(pokemon: Pokemon, dominantColorOrDefault: Color) {
    Card(
        modifier = Modifier.wrapContentHeight(),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = dominantColorOrDefault)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(URLDecoder.decode(pokemon.url, "UTF-8"))
                .crossfade(true)
                .apply { if (LocalInspectionMode.current) placeholder(R.drawable.pokeball) }
                .build(),
            contentDescription = "Member Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .fillMaxWidth(.8f)
                .aspectRatio(1f)
                .padding(16.dp)
        )
    }
}

@Composable
fun MemberName(pokemon: Pokemon, dominantColorOrDefault: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth(.8f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = dominantColorOrDefault,
            contentColor = Color.White
        )
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .fillMaxWidth(),
            text = pokemon.name.uppercase(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight(1000),
        )
    }
}