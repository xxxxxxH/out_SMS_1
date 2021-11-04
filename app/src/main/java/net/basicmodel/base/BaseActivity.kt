package net.basicmodel.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.basicmodel.entity.CountryEntity

/**
 * Copyright (C) 2021,2021/11/4, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
abstract class BaseActivity:AppCompatActivity() {

    var countryData:ArrayList<CountryEntity>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initView()
    }

    abstract fun getLayoutId():Int

    abstract fun initView()

    abstract fun initData()

    abstract fun setTitle():String
}