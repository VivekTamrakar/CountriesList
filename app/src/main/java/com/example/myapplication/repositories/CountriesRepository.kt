package com.example.myapplication.repositories

import com.example.myapplication.model.Country
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface CountriesList {
    @GET("countries.json")
    suspend fun getAllCountries(): List<Country>
}

class CountriesRepository {

    private val service = Retrofit.Builder()
        .baseUrl(Repository.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountriesList::class.java)

    suspend fun countries() = service.getAllCountries()

    object Repository {
        val BASE_URL =
            "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/"
    }
}

