package com.example.androidtemplateapp.ui.pokemondetails

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplateapp.entity.Stat
import com.example.androidtemplateapp.entity.StatX
import com.example.androidtemplateapp.entity.enums.StatNameEnum
import com.example.androidtemplateapp.ui.common.mocks.getPokemonStatListMock
import com.example.androidtemplateapp.ui.common.spacer.CustomSpacer

@Composable
fun PokemonStats(stats: List<Stat>) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        stats.forEach { stat ->
            PokemonStat(stat = stat)
            CustomSpacer(height = 4)
        }
    }
}

@Composable
fun PokemonStat(stat: Stat) {
    var startAnim by remember { mutableStateOf(false) }
    val animatedStatValue by animateFloatAsState(
        targetValue = if (startAnim) stat.baseStat / 100.toFloat() else .15f,
        animationSpec = tween(1000, 100),
        label = "StatProgressAnimation"
    )

    LaunchedEffect(Unit) { startAnim = true }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(.2f),
            text = LocalContext.current.getString(stat.statX.getAbbreviation()),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedStatValue)
                    .clip(CircleShape)
                    .background(stat.statX.getColor())
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = (animatedStatValue * 100).toInt().toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview("Pokemon Stats", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PokemonStatsPreview() {
    PokemonStats(getPokemonStatListMock())
}

@Preview("Pokemon Stat")
@Composable
fun PokemonStatPreview() {
    PokemonStat(
        Stat(
            baseStat = 50,
            effort = 50,
            statX = StatX(name = StatNameEnum.HP, url = "")
        )
    )
}