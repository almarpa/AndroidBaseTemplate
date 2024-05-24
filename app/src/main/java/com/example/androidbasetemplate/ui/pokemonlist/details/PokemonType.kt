package com.example.androidbasetemplate.ui.pokemonlist.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidbasetemplate.entity.TypeX
import com.example.androidbasetemplate.entity.TypeXX
import com.example.androidbasetemplate.entity.enums.PokemonTypeEnum

@Composable
fun PokemonType(types: List<TypeX>) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(type.typeXX.getColor())
                    .height(30.dp)
            ) {
                Text(
                    text = type.typeXX.name.value.uppercase(), color = Color.White, fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
@Preview("Pokemon Type")
fun PokemonTypePreview() {
    PokemonType(
        listOf(
            TypeX(1, TypeXX(PokemonTypeEnum.ICE, "")),
            TypeX(2, TypeXX(PokemonTypeEnum.PSYCHIC, "")),
        )
    )
}
