package com.example.pokebook

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokebook.data.model.Pokemon
import com.example.pokebook.data.model.RecentSearches
import com.example.pokebook.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel(){

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: MutableLiveData<Pokemon> = _pokemon

    private var searchItem: MutableList<RecentSearches> = mutableListOf()
    private val _addSearch = MutableLiveData<List<RecentSearches>>()
    val addSearch: MutableLiveData<List<RecentSearches>> = _addSearch

    private val _deleteSearch = MutableLiveData<List<RecentSearches>>()
    val deleteSearch: MutableLiveData<List<RecentSearches>> = _deleteSearch

    init {
        viewModelScope.launch {
            searchItem = pokemonRepository.getSearch().toMutableList()
            _addSearch.value = pokemonRepository.getSearch()
        }
    }

    fun searchPokemon(pokemon: String) {
        viewModelScope.launch {
            try {
                delay(750)
                val searchPokemon = pokemonRepository.getPokemon(pokemon)
                _pokemon.value = searchPokemon
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addSearch(search: String) {
        val recentSearches = RecentSearches(search)
        val check = searchItem.contains(recentSearches)
        viewModelScope.launch {
            if (!check) {
                pokemonRepository.insertSearch(recentSearches)
                searchItem.add(recentSearches)
                Log.e("addSearch:","${recentSearches.time}")
            } else {
                val time = System.currentTimeMillis()
                val index = searchItem.indexOf(recentSearches)
                searchItem[index].time = time
                pokemonRepository.updateSearch(time, search)
            }
            _addSearch.value = searchItem
        }
    }

    fun deleteSearch(recentSearches: RecentSearches) {
        viewModelScope.launch {
            pokemonRepository.deleteSearch(recentSearches)
            searchItem.remove(recentSearches)
            _deleteSearch.value = searchItem
        }
    }



}