package net.basicmodel.ui

import android.annotation.SuppressLint
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_activity_select.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import net.basicmodel.R
import net.basicmodel.adapter.NumberAdapter
import net.basicmodel.base.BaseActivity
import net.basicmodel.entity.CountryEntity
import net.basicmodel.entity.NumberEntity
import net.basicmodel.event.MessageEvent
import net.basicmodel.utils.Constant
import net.basicmodel.utils.DataHandleManager
import net.basicmodel.utils.RadioButtonManager
import net.basicmodel.utils.RequestManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Copyright (C) 2021,2021/11/4, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class SelectNumberActivity : BaseActivity(),CompoundButton.OnCheckedChangeListener {
    var numberAdapter:NumberAdapter?=null
    var data:ArrayList<NumberEntity>?=null
    var all:RadioButton?=null
    override fun getLayoutId(): Int {
        return R.layout.layout_activity_select
    }

    override fun initView() {
        titleMiddle.text = setTitle()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {
        EventBus.getDefault().register(this)
        RequestManager.get().getAllNumbers()
        val i = intent
        countryData = i.getSerializableExtra(Constant.country_data) as ArrayList<CountryEntity>?
        countryData?.let {
            initRadioButton(it)
        }?: kotlin.run {
            emptyView.visibility = View.VISIBLE
        }

    }

    override fun setTitle(): String {
        return this.getString(R.string.select)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initRadioButton(data: ArrayList<CountryEntity>) {
        all = RadioButtonManager.get().createRadioButton(this, this.getString(R.string.all))
        all!!.setOnCheckedChangeListener(this)
        all!!.isChecked = true
        countryRg.addView(all)
        for (item in data) {
            val radio = RadioButtonManager.get().createRadioButton(this, item.countryName)
            radio.setOnCheckedChangeListener(this)
            countryRg.addView(radio)
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        if (p1){
            val country = p0!!.text.toString()
            Log.i("xxxxxxH","country = $country")
            if (!TextUtils.equals(country,this.getString(R.string.all))){
                all!!.isChecked = false
                val data = DataHandleManager.get().getDataByCountry(country,this.data!!)
                numberAdapter?.setNewInstance(data)
            }else{
                all!!.isChecked = true
                numberAdapter?.setNewInstance(this.data)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent){
        val msg = event.getMessage()
        when(msg[0]){
            Constant.all_num_success -> {
                data = msg[1] as ArrayList<NumberEntity>
                numberAdapter = NumberAdapter(this,R.layout.layout_num_item,data!!)
                countryRecycler.layoutManager = LinearLayoutManager(this)
                countryRecycler.adapter = numberAdapter
            }
            Constant.all_num_failed -> {
                emptyView.visibility = View.VISIBLE
            }
        }
    }

}