package net.basicmodel.utils

import net.basicmodel.entity.CountryEntity
import net.basicmodel.entity.NumberEntity
import net.basicmodel.event.MessageEvent
import net.http.RequestService
import net.http.RetrofitUtils
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Copyright (C) 2021,2021/11/4, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class RequestManager {
    companion object {
        private var i: RequestManager? = null
            get() {
                field ?: run {
                    field = RequestManager()
                }
                return field
            }

        @Synchronized
        fun get(): RequestManager {
            return i!!
        }
    }

    fun getCountry() {
        RetrofitUtils.get().retrofit().create(RequestService::class.java).getCountry()
            .enqueue(object : Callback<ArrayList<CountryEntity>> {
                override fun onResponse(
                    call: Call<ArrayList<CountryEntity>>,
                    response: Response<ArrayList<CountryEntity>>
                ) {
                    EventBus.getDefault()
                        .post(MessageEvent(Constant.country_success, response.body()))
                }

                override fun onFailure(call: Call<ArrayList<CountryEntity>>, t: Throwable) {
                    EventBus.getDefault().post(MessageEvent(Constant.country_success))
                }

            })
    }

    fun getAllNumbers() {
        RetrofitUtils.get().retrofit().create(RequestService::class.java).getAllNumbers()
            .enqueue(object : Callback<ArrayList<NumberEntity>> {
                override fun onResponse(
                    call: Call<ArrayList<NumberEntity>>,
                    response: Response<ArrayList<NumberEntity>>
                ) {
                    EventBus.getDefault()
                        .post(MessageEvent(Constant.all_num_success, response.body()))
                }

                override fun onFailure(call: Call<ArrayList<NumberEntity>>, t: Throwable) {
                    EventBus.getDefault()
                        .post(MessageEvent(Constant.all_num_failed))
                }

            })
    }
}