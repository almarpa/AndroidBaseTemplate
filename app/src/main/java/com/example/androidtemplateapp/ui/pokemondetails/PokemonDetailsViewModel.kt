package com.example.androidtemplateapp.ui.pokemondetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.androidtemplateapp.common.utils.Resource
import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.entity.Pokemon
import com.example.androidtemplateapp.entity.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PokemonDetailsUiState {
    data object Loading : PokemonDetailsUiState
    data class Success(val details: PokemonDetails) : PokemonDetailsUiState
    data object Error : PokemonDetailsUiState
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _pokemon = savedStateHandle.toRoute<Pokemon>()
    private val _getDetailsTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {
        refreshData()
    }

    val pokemonDetails: StateFlow<PokemonDetailsUiState> =
        _getDetailsTrigger.filterNotNull().flatMapLatest { _ ->
            pokemonDetailsUseCase.getPokemonDetails(_pokemon.id)
                .map { pokemonDetails ->
                    when (pokemonDetails) {
                        is Resource.Loading -> PokemonDetailsUiState.Loading
                        is Resource.Error -> PokemonDetailsUiState.Error
                        is Resource.Success -> PokemonDetailsUiState.Success(pokemonDetails.data)
                    }
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PokemonDetailsUiState.Loading,
        )

    fun refreshData() {
        viewModelScope.launch {
            _getDetailsTrigger.emit(Unit)
        }
    }
}
