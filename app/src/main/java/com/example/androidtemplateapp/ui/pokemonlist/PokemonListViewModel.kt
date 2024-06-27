package com.example.androidtemplateapp.ui.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtemplateapp.domain.PokemonUseCase
import com.example.androidtemplateapp.entity.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState
    data class Success(val pokemonList: List<Pokemon>) : PokemonListUiState
    data object NotFound : PokemonListUiState
    data object Error : PokemonListUiState
}

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState

    private val _pokemonSearched = MutableLiveData("")
    val pokemonSearched: LiveData<String> = _pokemonSearched

    private var _visibleItems: Pair<Int, Int> = 0 to 0
    val visibleItems: Pair<Int, Int> = _visibleItems

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        resetCurrentScroll()
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.tryEmit(PokemonListUiState.Loading)
            pokemonUseCase.getPokemons()
                .catch {
                    _uiState.tryEmit(PokemonListUiState.Error)
                }
                .collect { pokemonList ->
                    _uiState.tryEmit(PokemonListUiState.Success(pokemonList))
                }
        }
    }

    fun onPokemonSearch(name: String) {
        saveCurrentSearch(name)
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.tryEmit(PokemonListUiState.Loading)
            pokemonUseCase.searchPokemonsByName(name)
                .catch {
                    _uiState.tryEmit(PokemonListUiState.Error)
                }
                .collect { pokemonList ->
                    if (pokemonList.isEmpty()) {
                        _uiState.tryEmit(PokemonListUiState.NotFound)
                    } else {
                        _uiState.tryEmit(PokemonListUiState.Success(pokemonList))
                    }
                }
        }
    }

    private fun saveCurrentSearch(name: String) {
        _pokemonSearched.value = name
    }

    fun removeCurrentSearch() {
        _pokemonSearched.value = ""
    }

    fun setCurrentScrollPosition(visibleItems: Pair<Int, Int>) {
        _visibleItems = visibleItems
    }

    private fun resetCurrentScroll() {
        _visibleItems = 0 to 0
    }
}
