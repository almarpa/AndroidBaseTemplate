package com.example.androidtemplateapp.ui.pokemondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplateapp.common.errorhandler.entity.AppError
import com.example.androidtemplateapp.domain.PokemonDetailsUseCase
import com.example.androidtemplateapp.entity.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PokemonDetailsUiState {
    data object Loading : PokemonDetailsUiState
    data class Success(val details: PokemonDetails) : PokemonDetailsUiState
    data class Error(val error: AppError) : PokemonDetailsUiState
}

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase,
) : ViewModel() {

    private val _detailsUiState =
        MutableStateFlow<PokemonDetailsUiState>(PokemonDetailsUiState.Loading)
    val detailsUiState: StateFlow<PokemonDetailsUiState> = _detailsUiState

    fun getPokemonDetails(pokemonID: Int) {
        viewModelScope.launch {
            _detailsUiState.emit(PokemonDetailsUiState.Loading)
            pokemonDetailsUseCase.getPokemonDetails(pokemonID)
                .fold(
                    { appError ->
                        _detailsUiState.emit(PokemonDetailsUiState.Error(appError))
                    },
                    { pokemonDetails ->
                        _detailsUiState.emit(PokemonDetailsUiState.Success(pokemonDetails))
                    },
                )
        }
    }
}
