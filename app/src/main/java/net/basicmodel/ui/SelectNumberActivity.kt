package net.basicmodel.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.layout_activity_select.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import net.basicmodel.R
import net.basicmodel.adapter.NumberAdapter
import net.basicmodel.base.BaseActivity
import net.basicmodel.entity.CountryEntity
import net.basicmodel.entity.NumberEntity
import net.basicmodel.event.MessageEvent
import net.basicmodel.utils.*
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
class SelectNumberActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener,
    OnItemClickListener {
    var numberAdapter: NumberAdapter? = null
    var data: ArrayList<NumberEntity>? = null
    var all: RadioButton? = null
    override fun getLayoutId(): Int {
        return R.layout.layout_activity_select
    }

    override fun initView() {
        titleMiddle.text = setTitle()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {
        EventBus.getDefault().register(this)
        LoadingDialogManager.get().show(this)
        RequestManager.get().getAllNumbers()
        val i = intent
        countryData = i.getSerializableExtra(Constant.country_data) as ArrayList<CountryEntity>?
        countryData?.let {
            initRadioButton(it)
        } ?: kotlin.run {
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
        if (p1) {
            data?.let {
                val country = p0!!.text.toString()
                Log.i("xxxxxxH", "country = $country")
                if (it.size>0){
                    for (item in it){
                        item.isChecked = false
                    }
                }
                if (!TextUtils.equals(country, this.getString(R.string.all))) {
                    all!!.isChecked = false
                    val data = DataHandleManager.get().getDataByCountry(country, it)
                    if (data.size > 0){
                        data[0].isChecked = true
                    }else{

                    }
                    numberAdapter?.setNewInstance(data)
                } else {
                    all!!.isChecked = true
                    it[0].isChecked = true
                    numberAdapter?.setNewInstance(it)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val origin  = adapter.data as ArrayList<NumberEntity>
        val entity = origin[position]
        for (item in origin){
            item.isChecked = item == entity
        }
        adapter.notifyDataSetChanged()
        val i = Intent(this, DetailsActivity::class.java)
        i.putExtra(Constant.country_data, entity)
        startActivity(i)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        when (msg[0]) {
            Constant.all_num_success -> {
                data = msg[1] as ArrayList<NumberEntity>
                data?.let {
                    it[0].isChecked = true
                    numberAdapter = NumberAdapter(this, R.layout.layout_num_item, it)
                    numberAdapter!!.setOnItemClickListener(this)
                    countryRecycler.layoutManager = LinearLayoutManager(this)
                    countryRecycler.adapter = numberAdapter
                } ?: kotlin.run {
                    Toast.makeText(this, this.getString(R.string.empty), Toast.LENGTH_SHORT).show()
                }
                LoadingDialogManager.get().close()
            }
            Constant.all_num_failed -> {
                emptyView.visibility = View.VISIBLE
                Toast.makeText(this, this.getString(R.string.empty), Toast.LENGTH_SHORT).show()
                LoadingDialogManager.get().close()
            }
        }
    }


}