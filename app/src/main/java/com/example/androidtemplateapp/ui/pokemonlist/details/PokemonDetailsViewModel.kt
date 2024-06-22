package com.example.androidtemplateapp.ui.pokemonlist.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.entity.Pokemon.Companion.getPokemonFromJson
import com.example.androidtemplateapp.entity.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pokemon = savedStateHandle.getStateFlow<String?>("pokemon", null)

    val pokemonDetails: StateFlow<PokemonDetails?> =
        pokemon.filterNotNull().flatMapLatest { pokemon ->
            pokemonDetailsUseCase.getPokemonDetails(pokemon.getPokemonFromJson().id)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )
}
