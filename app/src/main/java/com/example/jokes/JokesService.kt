package com.example.jokes

import retrofit2.Call
import retrofit2.http.GET

interface JokesService {
    @GET("random_joke")
    fun getJokes():Call<JokesData>
}