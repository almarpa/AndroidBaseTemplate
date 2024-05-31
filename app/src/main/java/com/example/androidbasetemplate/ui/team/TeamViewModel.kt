package com.example.androidbasetemplate.ui.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TeamUiState {
    data object Loading : TeamUiState
    data class Success(val teamList: List<Pokemon>) : TeamUiState
    data object Error : TeamUiState
}

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<TeamUiState>(TeamUiState.Loading)
    val uiState: StateFlow<TeamUiState> = _uiState

    init {
        getTeamList()
    }

    fun getTeamList() {
        _uiState.tryEmit(TeamUiState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            pokemonUseCase.getTeamMembers()
                .catch {
                    _uiState.tryEmit(TeamUiState.Error)
                }
                .collect { pokemonList ->
                    _uiState.tryEmit(TeamUiState.Success(pokemonList))
                }
        }
    }

    fun addPokemonToTeam(pokemon: Pokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonUseCase.addPokemonToTeam(pokemon)
        }
    }
}
