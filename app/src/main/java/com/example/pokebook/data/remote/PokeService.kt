package com.example.pokebook.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokebook.data.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {

    @GET("pokemon/{pokemon}")
    suspend fun getPokemon(@Path("pokemon") pokemon: String): Pokemon
}