package net.basicmodel.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.layout_activity_details.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import net.basicmodel.R
import net.basicmodel.adapter.MsgRecordAdapter
import net.basicmodel.base.BaseActivity
import net.basicmodel.entity.MsgRecordEntity
import net.basicmodel.entity.NumberEntity
import net.basicmodel.entity.RecordItemEntity
import net.basicmodel.event.MessageEvent
import net.basicmodel.utils.Constant
import net.basicmodel.utils.CopyUtils
import net.basicmodel.utils.LoadingDialogManager
import net.basicmodel.utils.RequestManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Copyright (C) 2021,2021/11/5, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class DetailsActivity : BaseActivity(), OnItemClickListener {
    var entity: NumberEntity? = null
    var number: String = ""
    var recordAdapter: MsgRecordAdapter? = null
    var data: MsgRecordEntity? = null
    override fun getLayoutId(): Int {
        return R.layout.layout_activity_details
    }

    override fun initView() {
        detailsRefresh.setOnClickListener {
            if (!TextUtils.isEmpty(number)) {
                LoadingDialogManager.get().show(this)
                RequestManager.get().getMsgRecord(number)
            }
        }
        detailsNum.setOnClickListener {
            CopyUtils().copy(this, detailsNum.text.toString())
        }

    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        EventBus.getDefault().register(this)
        val i = intent
        entity = i.getSerializableExtra(Constant.country_data) as NumberEntity
        entity?.let {
            titleMiddle.text = "${this.getString(R.string.details)} ${it.number}"
            detailsNum.text = it.number
            titleLeft.setImageResource(R.mipmap.back)
            titleLeft.setOnClickListener {
                finish()
            }
            Glide.with(this).load(it.imageUrl)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(12)))
                .into(detailsImg)
            number = it.number
            RequestManager.get().getMsgRecord(it.number)
            LoadingDialogManager.get().show(this)
        }
    }

    override fun setTitle(): String {
        return ""
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val msg = event.getMessage()
        when (msg[0]) {
            Constant.msg_record_success -> {
                data = msg[1] as MsgRecordEntity
                data?.let {
                    if (it.content?.size != 0) {
//                        it.content!![0].isChecked = true
                        recordAdapter = MsgRecordAdapter(R.layout.layout_item_msg, it.content)
                        recordAdapter!!.setOnItemClickListener(this)
                        detailsRecycler.layoutManager = LinearLayoutManager(this)
                        detailsRecycler.adapter = recordAdapter
                    } else {
                        Toast.makeText(this, this.getString(R.string.empty), Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                LoadingDialogManager.get().close()
            }
            Constant.msg_record_failed -> {
                Toast.makeText(this, this.getString(R.string.empty), Toast.LENGTH_SHORT).show()
                LoadingDialogManager.get().close()
            }

        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val origin = adapter.data as ArrayList<RecordItemEntity>
        val entity = origin[position]
        for (item in origin) {
            item.isChecked = item == entity
        }
        adapter.notifyDataSetChanged()
        val i = Intent(this, NumActivity::class.java)
        i.putExtra(Constant.code_data, entity)
        startActivity(i)
    }
}