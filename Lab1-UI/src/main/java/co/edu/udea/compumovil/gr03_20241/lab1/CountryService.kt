package co.edu.udea.compumovil.gr03_20241.lab1

import co.edu.udea.compumovil.gr03_20241.lab1.models.City
import co.edu.udea.compumovil.gr03_20241.lab1.models.Country
import co.edu.udea.compumovil.gr03_20241.lab1.models.Entity
import co.edu.udea.compumovil.gr03_20241.lab1.models.GeoNamesResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RestCountriesService {
    @GET("all")
    fun getCountries(): Call<List<Country>>
}

interface GeoNamesService {
    @GET("searchJSON")
    fun getCities(
        @Query("country") country: String,
        @Query("username") username: String
    ): Call<GeoNamesResponse>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://restcountries.com/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val countriesService = retrofit.create(RestCountriesService::class.java)

val geoNamesRetrofit = Retrofit.Builder()
    .baseUrl("http://api.geonames.org/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val citiesService = geoNamesRetrofit.create(GeoNamesService::class.java)
