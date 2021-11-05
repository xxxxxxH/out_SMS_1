package net.http


import net.basicmodel.entity.CountryEntity
import net.basicmodel.entity.MsgRecordEntity
import net.basicmodel.entity.NumberEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestService {
    @GET("countries")
    fun getCountry(): Call<ArrayList<CountryEntity>>

    @GET("numbers")
    fun getAllNumbers(): Call<ArrayList<NumberEntity>>

    @GET("number-records")
    fun getMsgRecord(@Query("number") number: String): Call<MsgRecordEntity>
}