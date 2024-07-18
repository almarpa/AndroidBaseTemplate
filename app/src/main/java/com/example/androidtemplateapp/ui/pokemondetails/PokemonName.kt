package com.example.androidtemplateapp.ui.pokemondetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material.icons.outlined.PersonAdd
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.ui.common.mocks.getPokemonMock
import com.example.androidtemplateapp.ui.common.preview.TemplatePreviewTheme
import java.util.Locale

@Composable
fun PokemonName(pokemon: Pokemon, onMemberClick: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AddMemberButton(pokemon.isTeamMember) { onMemberClick(it) }
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
fun AddMemberButton(isMemberYet: Boolean, onMemberClick: (Boolean) -> Unit) {
    var isTeamMember by remember { mutableStateOf(isMemberYet) }
    val memberIconScale by animateFloatAsState(
        targetValue = if (isTeamMember) 1.5f else 1f,
        label = "Member Button Scale"
    )
    IconButton(
        modifier = Modifier
            .fillMaxWidth(.1f)
            .padding(vertical = 4.dp)
            .scale(memberIconScale),
        onClick = {
            isTeamMember = !isTeamMember
            onMemberClick(isTeamMember)
        },
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = if (isTeamMember) Icons.Filled.PersonRemove else Icons.Outlined.PersonAdd,
            contentDescription = "Add member icon",
            tint = MaterialTheme.colorScheme.primary,
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