package net.basicmodel.adapter

import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import net.basicmodel.R
import net.basicmodel.entity.RecordItemEntity

/**
 * Copyright (C) 2021,2021/11/5, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class MsgRecordAdapter(layoutResId: Int, data: ArrayList<RecordItemEntity>?) :
    BaseQuickAdapter<RecordItemEntity, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: RecordItemEntity) {
        holder.getView<LinearLayout>(R.id.recordRoot).setBackgroundResource(
            if (item.isChecked)
                R.drawable.msg_item_firset_bg
            else
                R.drawable.msg_item_bg
        )
        holder.setText(R.id.timeStr, item.relativeTime)
            .setText(R.id.fromStr, item.smsFrom.trim())
            .setText(R.id.msgStr, item.content)
    }
}