package net.basicmodel.ui

import android.annotation.SuppressLint
import android.os.Build
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.layout_activity_select.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import net.basicmodel.R
import net.basicmodel.base.BaseActivity
import net.basicmodel.entity.CountryEntity
import net.basicmodel.utils.Constant
import net.basicmodel.utils.RadioButtonManager

/**
 * Copyright (C) 2021,2021/11/4, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class SelectNumberActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.layout_activity_select
    }

    override fun initView() {
        titleMiddle.text = setTitle()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {
        val i = intent
        countryData = i.getSerializableExtra(Constant.country_data) as ArrayList<CountryEntity>?
        initRadioButton(countryData!!)
    }

    override fun setTitle(): String {
        return this.getString(R.string.select)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initRadioButton(data: ArrayList<CountryEntity>) {
        countryRg.addView(RadioButtonManager.get().createRadioButton(this, "All"))
        for (item in data) {
            countryRg.addView(RadioButtonManager.get().createRadioButton(this, item.countryName))
        }
//        countryRg.setOnCheckedChangeListener()
    }

}