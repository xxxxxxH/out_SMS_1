package net.http


import net.basicmodel.entity.CountryEntity
import net.basicmodel.entity.NumberEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestService {
    @GET("countries")
    fun getCountry():Call<ArrayList<CountryEntity>>

    @GET("numbers")
    fun getAllNumbers():Call<ArrayList<NumberEntity>>
}