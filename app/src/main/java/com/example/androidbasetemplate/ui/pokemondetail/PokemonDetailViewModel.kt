package com.example.androidbasetemplate.ui.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasetemplate.domain.PokemonUseCase
import com.example.androidbasetemplate.entity.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _pokemon = MutableLiveData<PokemonDetails>()
    val pokemon: LiveData<PokemonDetails>
        get() = _pokemon

    fun getPokemonDetail(pokemonID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _pokemon.postValue(pokemonUseCase.getPokemon(pokemonID))
        }
    }
}
